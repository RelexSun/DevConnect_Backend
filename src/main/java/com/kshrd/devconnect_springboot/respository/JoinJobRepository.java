package com.kshrd.devconnect_springboot.  respository;
import com.kshrd.devconnect_springboot.model.dto.response.JoinJobResponse;
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
        FROM join_jobs
        WHERE join_job_id = #{id}
    """)
    @Results(id = "BaseResultMap", value = {
            @Result(property = "joinJobId", column = "join_job_id"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "developer", column = "user_id" , one = @One(select = "com.kshrd.devconnect_springboot.respository.AppUserRepository.getUserById")),
    })
    JoinJob selectJoinJobById(@Param("id") UUID id);
    
    // DELETE JoinJob
    @Select("""
        DELETE
        FROM join_jobs
        WHERE join_job_id = #{joinJobId} AND is_approve = false
        RETURNING *
        """)
        @ResultMap("BaseResultMap")
    JoinJob deleteJoinJob(UUID joinJobId);

    // INSERT JoinJob
    @Select("""
        INSERT INTO join_jobs
        (title, description, job_id, user_id, cv)
        VALUES
        (
            #{joinJob.title},
            #{joinJob.description},
            #{jobId},
            #{developerId},
            #{cv}
        )
        RETURNING *;
        """)
        @ResultMap("BaseResultMap")
    JoinJob insertJoinJob(@Param("joinJob") JoinJobRequest entity , String cv , UUID developerId , @Param("jobId") UUID jobId);

    // GET ALL JoinJob
    @Select("""
        SELECT * FROM join_jobs
        where user_id = #{developerId}
    """)
    @ResultMap("BaseResultMap")
    List<JoinJob> getAllJoinJob(UUID developerId);

    // UPDATE IS APPROVE
    @Select("""
        UPDATE join_jobs
        SET is_approve = #{isApprove}
        WHERE join_job_id = #{joinJobId}
        RETURNING *
    """)
    @ResultMap("BaseResultMap")
    JoinJob updateIsApprove(@Param("isApprove") Boolean isApprove , @Param("joinJobId") UUID joinJobId);

    // GET ALL JOIN JOB BY STATUS
    @Select("""
        SELECT * FROM join_jobs
        where is_approve = #{isApprove}
    """)
    @ResultMap("BaseResultMap")
    List<JoinJob> getAllJoinJobByStatus(@Param("isApprove") Boolean isApprove);
}
