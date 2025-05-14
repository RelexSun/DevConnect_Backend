package com.kshrd.devconnect_springboot.respository;

import com.kshrd.devconnect_springboot.model.dto.request.JoinProjectRequest;
import com.kshrd.devconnect_springboot.model.entity.JoinProject;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface JoinProjectRepository {
    @Results(id = "baseMapper", value = {
            @Result(property = "isApproved", column = "is_approved"),
            @Result(property = "projectId", column = "project_id"),
            @Result(property = "position", column = "position_id", one = @One(select = "com.kshrd.devconnect_springboot.respository.PositionRepository.getPositionById")),
            @Result(property = "developer", column = "user_id", one = @One(select = "com.kshrd.devconnect_springboot.respository.AppUserRepository.getUserResponseById")),
    })
    @Select("""
        SELECT * FROM join_projects WHERE project_id = #{projectId}
    """)
    List<JoinProject> getAllJoinProjectByProjectId(UUID projectId);

    @ResultMap("baseMapper")
    @Select("""
        SELECT * FROM join_projects WHERE project_id = #{projectId} AND is_approved = true
    """)
    List<JoinProject> getAllJoinProjectByProjectIdAndApproved(UUID projectId);

    @ResultMap("baseMapper")
    @Select("""
        SELECT * FROM join_projects WHERE project_id = #{projectId} AND is_approved = false
    """)
    List<JoinProject> getAllJoinProjectByProjectIdAndDeny(UUID projectId);

    @ResultMap("baseMapper")
    @Select("""
        INSERT INTO join_projects VALUES (#{jp.title}, #{jp.description}, DEFAULT, #{jp.projectId}, #{jp.developerId}, #{jp.positionId})
        RETURNING *;
    """)
    JoinProject createJoinProject(@Param("jp") JoinProjectRequest jp);

    @Delete("""
        DELETE FROM join_projects WHERE project_id = #{projectId}
    """)
    void deleteAllJoinProject(UUID projectId);

    @Select("""
        SELECT COUNT(*) FROM join_projects WHERE project_id = #{projectId} AND position_id = #{positionId} AND is_approved = true
    """)
    Integer getApprovedCount(UUID projectId, UUID positionId);

    @ResultMap("baseMapper")
    @Select("""
        SELECT * FROM join_projects WHERE developer_id = #{developerId}
    """)
    JoinProject getJoinProjectByDeveloperId(UUID developerId);

    @Update("""
        UPDATE join_projects SET is_approved = #{status} WHERE project_id = #{projectId} AND developer_id = #{developerId}
    """)
    void updateApprovalStatus(Boolean status, UUID projectId, UUID developerId);
}
