package com.kshrd.devconnect_springboot.respository;

import com.kshrd.devconnect_springboot.config.JobBoardTypeHandler;
import com.kshrd.devconnect_springboot.model.dto.request.ResumeRequest;
import com.kshrd.devconnect_springboot.model.entity.Resume;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface ResumeRepository {
 
    // GET Resume BY ID
    @Select("""
        SELECT *
        FROM resume
        WHERE resume_id = #{id}
    """)
    @Results(id = "BaseResultMap", value = {
            @Result(property = "resumeId", column = "resume_id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "address", column = "address"),
            @Result(property = "email", column = "email"),
            @Result(property = "dob", column = "dob"),
            @Result(property = "position", column = "position"),
            @Result(property = "description", column = "description"),
            @Result(property = "information", column = "information" , typeHandler = JobBoardTypeHandler.class),
            @Result(property = "developerId", column = "developer_id")
    })
    Resume selectResumesById(@Param("id") UUID id);
    
    // DELETE Resume
    @Select("""
        DELETE
        FROM resume
        WHERE resume_id = #{resumeId}
        RETURNING *
        """)
        @ResultMap("BaseResultMap")
    Resume deleteResumes(UUID resumeId);

    // INSERT Resume
    @Select("""
        INSERT INTO resume
        (fullname, phone_number, address, email, dob, position, description, information, developer_id)
        VALUES
        (
            #{resume.fullName},
            #{resume.phoneNumber},
            #{resume.address},
            #{resume.email},
            #{resume.dob},
            #{resume.position},
            #{resume.description},
            #{resume.information, typeHandler=com.kshrd.devconnect_springboot.config.ResumeInformationTypeHandler},
            #{developerId}
        )
        RETURNING *;
        """)
        @ResultMap("BaseResultMap")
   
    Resume insertResumes(@Param("resume") ResumeRequest entity , UUID developerId);
    // UPDATE  Resume
    @Select("""
    UPDATE resume
    SET
         fullname = #{resume.fullName},
         phone_number = #{resume.phoneNumber},
         address = #{resume.address},
         email = #{resume.email},
         dob = #{resume.dob},
         position = #{resume.position},
         description = #{resume.description},
         information = #{resume.information, typeHandler=com.kshrd.devconnect_springboot.config.ResumeInformationTypeHandler},
         developer_id = #{developerId}
    WHERE resume_id = #{id}
    RETURNING *;
    """)
    @ResultMap("BaseResultMap")
    
    Resume updateResumes(UUID id , @Param("resume") ResumeRequest entity , UUID developerId);

    // GET Resume BY Developer ID
    @Select("""
        SELECT *
        FROM resume
        WHERE developer_id = #{developerId}
    """)
    @ResultMap("BaseResultMap")
    List<Resume> selectResumesByDeveloperId(@Param("developerId") UUID developerId);

}
