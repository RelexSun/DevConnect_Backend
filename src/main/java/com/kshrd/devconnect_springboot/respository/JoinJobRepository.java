package com.kshrd.devconnect_springboot.  respository;
import org.apache.ibatis.annotations.*;
import java.util.List;
import com.kshrd.devconnect_springboot.model.entity.JoinJob;
import com.kshrd.devconnect_springboot.model.dto.request.JoinJobRequest;
import java.util.UUID;

@Mapper
public interface JoinJobRepository {
 
    // GET JoinJob BY ID
    @Select("""
        SELECT *
        FROM join_job
        WHERE join_job_id = #{id}
    """)
    @Results(id = "BaseResultMap", value = {
            @Result(property = "joinJobId", column = "join_job_id"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "isApprove", column = "is_approve"),
            @Result(property = "jobId", column = "job_id"),
            @Result(property = "developerId", column = "developer_id")
    })
    JoinJob selectJoinJobById(@Param("id") UUID id);
    
    // DELETE JoinJob
    @Select("""
        DELETE
        FROM join_job
        WHERE join_job_id = #{joinJobId}
        RETURNING *
        """)
        @ResultMap("BaseResultMap")
    JoinJob deleteJoinJob(UUID joinJobId);

    // INSERT JoinJob
    @Select("""
        INSERT INTO join_job
        (title, description, job_id, developer_id ,cv)
        VALUES
        (
            #{joinJob.title},
            #{joinJob.description},
            #{joinJob.jobId},
            #{developerId},
            #{cv}
        )
        RETURNING *;
        """)
        @ResultMap("BaseResultMap")
    JoinJob insertJoinJob(@Param("joinJob") JoinJobRequest entity , String cv , UUID developerId);

    // GET ALL JoinJob
    @Select("""
        SELECT * FROM join_job
        where developer_id = #{developerId}
    """)
    @ResultMap("BaseResultMap")
    List<JoinJob> getAllJoinJob(UUID developerId);

    // UPDATE IS APPROVE
    @Select("""
        UPDATE join_job
        SET is_approve = #{isApprove}
        WHERE join_job_id = #{joinJobId}
        RETURNING *
    """)
    @ResultMap("BaseResultMap")
    JoinJob updateIsApprove(@Param("isApprove") Boolean isApprove , @Param("joinJobId") UUID joinJobId);

    // GET ALL JOIN JOB BY STATUS
    @Select("""
        SELECT * FROM join_job
        where is_approve = #{isApprove}
    """)
    @ResultMap("BaseResultMap")
    List<JoinJob> getAllJoinJobByStatus(@Param("isApprove") Boolean isApprove);
}
