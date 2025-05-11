package com.kshrd.devconnect_springboot.  respository;
import org.apache.ibatis.annotations.*;
import java.util.List;
import com.kshrd.devconnect_springboot.model.entity.Jobs;
import com.kshrd.devconnect_springboot.model.dto.request.JobsRequest;
import java.util.UUID;

@Mapper
public interface JobsRepository {
 
    // GET Jobs BY ID
    @Select("""
        SELECT *
        FROM jobs
        WHERE job_id = #{id}
    """)
    @Results(id = "BaseResultMap", value = {
            @Result(property = "jobId", column = "job_id"),
            @Result(property = "title", column = "title"),
            @Result(property = "salary", column = "salary"),
            @Result(property = "location", column = "location"),
            @Result(property = "status", column = "status"),
            @Result(property = "description", column = "description"),
            @Result(property = "jobBoard", column = "job_board", typeHandler = com.kshrd.devconnect_springboot.config.JobBoardTypeHandler.class),
            @Result(property = "pax", column = "pax"),
            @Result(property = "jobType", column = "job_type" , one = @One(select = "com.kshrd.devconnect_springboot.respository.JobsRepository.selectJobTypeById")),
            @Result(property = "postedDate", column = "posted_date"),
            @Result(property = "creator", column = "creator_id" , one = @One(select = "com.kshrd.devconnect_springboot.respository.AppUserRepository.getUserById")),
    })
    
    Jobs selectJobsById(@Param("id") UUID id);
    
    // DELETE Jobs
    @Select("""
        DELETE
        FROM jobs
        WHERE job_id = #{jobId}
        RETURNING *
        """)
        @ResultMap("BaseResultMap")
    Jobs deleteJobs(UUID jobId);

    // GET Jobs BY Creator ID
    @Select("""
        SELECT *
        FROM jobs
        WHERE creator_id = #{creatorId}
    """)
    @ResultMap("BaseResultMap")
    List<Jobs> selectJobsByCreatorId(@Param("creatorId") UUID creatorId);

    // INSERT Jobs
    @Select("""
        INSERT INTO jobs
        (title, salary, location, status, description, posted_date, creator_id , job_board , job_type , pax)
        VALUES
        (
            #{jobs.title},
            #{jobs.salary},
            #{jobs.location},
            #{jobs.status},
            #{jobs.description},
            #{jobs.postedDate},
            #{creatorId},
            #{jobs.jobBoard, typeHandler=com.kshrd.devconnect_springboot.config.JobBoardTypeHandler},
            #{jobTypeId},
            #{jobs.pax}
        )
        RETURNING *;
        """)
        @ResultMap("BaseResultMap")
    Jobs insertJobs(@Param("jobs") JobsRequest entity , UUID creatorId , UUID jobTypeId);

    // UPDATE  Jobs
    @Select("""
    UPDATE jobs
    SET
         title = #{jobs.title},
         salary = #{jobs.salary},
         location = #{jobs.location},
         status = #{jobs.status},
         description = #{jobs.description},
         job_board = #{jobs.jobBoard, typeHandler=com.kshrd.devconnect_springboot.config.JobBoardTypeHandler},
         posted_date = #{jobs.postedDate},
         job_type = #{jobTypeId},
         pax = #{jobs.pax}
         WHERE job_id = #{id}
    RETURNING *;
    """)
    @ResultMap("BaseResultMap")
    Jobs updateJobs(UUID id , @Param("jobs") JobsRequest entity , UUID jobTypeId);

    // GET ALL Jobs
    @Select("""
        SELECT * FROM jobs
        offset #{size} * (#{page} - 1)
        limit #{size}
    """)
    @ResultMap("BaseResultMap")
    List<Jobs> getAllJobs(Integer page, Integer size);

    // UPDATE STATUS
    @Select("""
        UPDATE jobs
        SET status = #{status}
        WHERE job_id = #{jobId}
        RETURNING *
    """)
    @ResultMap("BaseResultMap")
    Jobs updateStatus(@Param("jobId") UUID jobId, @Param("status") Boolean status);

    // GET JOBS TYPE BY JOB TYPE ID
    @Select("""
        SELECT type_name
        FROM job_types
        WHERE job_type_id = #{jobTypeId}
    """)
    String selectJobTypeById(@Param("jobTypeId") UUID jobTypeId);
}
