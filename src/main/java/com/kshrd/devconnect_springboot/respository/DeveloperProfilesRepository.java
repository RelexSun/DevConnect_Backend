package com.kshrd.devconnect_springboot.  respository;
import org.apache.ibatis.annotations.*;
import java.util.List;
import com.kshrd.devconnect_springboot.model.entity.DeveloperProfiles;
import com.kshrd.devconnect_springboot.model.dto.request.DeveloperProfilesRequest;
import java.util.UUID;

@Mapper
public interface DeveloperProfilesRepository {
 
    // GET DeveloperProfiles BY ID
    @Select("""
        SELECT * 
        FROM developer_profiles 
        WHERE developer_id = #{id}
    """)
    @Results(id = "BaseResultMap", value = {
            @Result(property = "developerId", column = "developer_id"),
            @Result(property = "bio", column = "bio"),
            @Result(property = "address", column = "address"),
            @Result(property = "coverPicture", column = "cover_picture"),
            @Result(property = "cv", column = "cv"),
            @Result(property = "githubUsername", column = "github_username"),
            @Result(property = "topComment", column = "top_comment"),
            @Result(property = "mvpCount", column = "mvp_count"),
            @Result(property = "topOneCount", column = "top_one_count"),
            @Result(property = "employeeStatus", column = "employee_status"),
            @Result(property = "jobTypeId", column = "job_type_id"),
            @Result(property = "userId", column = "user_id")
    })
    
    DeveloperProfiles selectDeveloperProfilesById(@Param("id") UUID id);
    
    // DELETE DeveloperProfiles
    @Select("""
        DELETE 
        FROM developer_profiles 
        WHERE developer_id = #{developerId}
        RETURNING *
        """)
        @ResultMap("BaseResultMap")
    DeveloperProfiles deleteDeveloperProfiles(UUID developerId);

    // INSERT DeveloperProfiles
    @Select("""
        INSERT INTO developer_profiles 
        (bio, address, cover_picture, cv, github_username, top_comment, mvp_count, top_one_count, employee_status, job_type_id, user_id)
        VALUES 
        (
            #{developerProfiles.bio}, 
            #{developerProfiles.address}, 
            #{developerProfiles.coverPicture}, 
            #{developerProfiles.cv}, 
            #{developerProfiles.githubUsername}, 
            #{developerProfiles.topComment}, 
            #{developerProfiles.mvpCount}, 
            #{developerProfiles.topOneCount}, 
            #{developerProfiles.employeeStatus}, 
            #{developerProfiles.jobTypeId}, 
            #{developerProfiles.userId}
        )
        RETURNING *;
        """)
        @ResultMap("BaseResultMap")
   
    DeveloperProfiles insertDeveloperProfiles(@Param("developerProfiles") DeveloperProfilesRequest entity);

    // UPDATE  DeveloperProfiles
    @Select("""
    UPDATE developer_profiles 
    SET
         bio = #{developerProfiles.bio},
         address = #{developerProfiles.address},
         cover_picture = #{developerProfiles.coverPicture},
         cv = #{developerProfiles.cv},
         github_username = #{developerProfiles.githubUsername},
         top_comment = #{developerProfiles.topComment},
         mvp_count = #{developerProfiles.mvpCount},
         top_one_count = #{developerProfiles.topOneCount},
         employee_status = #{developerProfiles.employeeStatus},
         job_type_id = #{developerProfiles.jobTypeId},
         user_id = #{developerProfiles.userId}
    WHERE developer_id = #{id}
    RETURNING *;
    """)
    @ResultMap("BaseResultMap")
    
    DeveloperProfiles updateDeveloperProfiles(UUID id , @Param("developerProfiles") DeveloperProfilesRequest entity);
    
    // GET ALL DeveloperProfiles
        
    @Select("""
        SELECT * FROM developer_profiles
    """)
    @ResultMap("BaseResultMap")
    
    List<DeveloperProfiles> getAllDeveloperProfiles();
}
