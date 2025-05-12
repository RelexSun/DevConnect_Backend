package com.kshrd.devconnect_springboot.respository;

import com.kshrd.devconnect_springboot.config.ResumeInformationTypeHandler;
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
        FROM resumes
        WHERE user_id = #{id}
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
            @Result(property = "information", column = "information" , typeHandler = ResumeInformationTypeHandler.class),
            @Result(property = "developerId", column = "user_id")
    })
    Resume selectCurrentResumes(@Param("id") UUID id);
    
    // DELETE Resume
    @Select("""
        DELETE
        FROM resumes
        WHERE user_id = #{userId}
        RETURNING *
        """)
        @ResultMap("BaseResultMap")
    Resume deleteResumes(UUID userId);

    // INSERT Resume
    @Select("""
        INSERT INTO resumes
        (fullname, phone_number, address, email, dob, position, description, information, user_id)
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
    UPDATE resumes
    SET
         fullname = #{resume.fullName},
         phone_number = #{resume.phoneNumber},
         address = #{resume.address},
         email = #{resume.email},
         dob = #{resume.dob},
         position = #{resume.position},
         description = #{resume.description},
         information = #{resume.information, typeHandler=com.kshrd.devconnect_springboot.config.ResumeInformationTypeHandler}
    WHERE user_id = #{developerId}
    RETURNING *;
    """)
    @ResultMap("BaseResultMap")
    Resume updateResumes(@Param("resume") ResumeRequest entity , UUID developerId);

}
