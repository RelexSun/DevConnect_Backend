package com.kshrd.devconnect_springboot.respository;

import com.kshrd.devconnect_springboot.model.entity.JoinProject;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface JoinProjectRepository {
    @Results(id = "baseMapper", value = {
            @Result(property = "isApproved", column = "is_approved"),
            @Result(property = "projectId", column = "project_id"),
            @Result(property = "positionId", column = "position_id"),
            @Result(property = "developerId", column = "user_id")
    })
    @Select("""
        SELECT * FROM join_projects WHERE project_id = #{projectId}
    """)
    List<JoinProject> getAllJoinProjectByProjectId(UUID projectId);

    @Select("""
        INSERT INTO join_projects VALUES (#{jp.title}, #{jp.description}, #{jp.isApproved}, #{jp.projectId}, #{jp.positionId}, #{jp.developerId})
        RETURNING *;
    """)
    JoinProject createJoinProject(JoinProject jp);

    @Delete("""
        DELETE FROM join_projects WHERE project_id = #{projectId}
    """)
    void deleteAllJoinProject(UUID projectId);
}
