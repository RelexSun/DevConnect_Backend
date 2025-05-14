package com.kshrd.devconnect_springboot.respository;

import com.kshrd.devconnect_springboot.model.dto.request.RecruiterRequest;
import com.kshrd.devconnect_springboot.model.dto.response.AppUserResponse;
import com.kshrd.devconnect_springboot.model.entity.Recruiter;
import com.kshrd.devconnect_springboot.model.enums.Gender;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.UUID;

@Mapper
public interface RecruiterRepository {
    @Results(id = "baseMapper", value = {
            @Result(property = "recruiterId", column = "recruiter_id"),
            @Result(property = "companyName", column = "company_name"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "companyLocation", column = "company_location"),
            @Result(property = "establishDate", column = "establish_date"),
            @Result(property = "coverPicture", column = "cover_picture"),
            @Result(property = "userInformation", column = "user_id", one = @One(select = "com.kshrd.devconnect_springboot.respository.AppUserRepository.getUserById"))
    })
    @Select("""
        SELECT * FROM recruiter_profiles WHERE user_id = #{userId}
    """)
    Recruiter getRecruiterProfile(UUID userId);

    @ResultMap("baseMapper")
    @Select("""
        INSERT INTO recruiter_profiles VALUES (DEFAULT,
                                           #{req.companyName},
                                           #{req.gender},
                                           #{req.phoneNumber},
                                           #{req.industry},
                                           #{req.companyLocation},
                                           #{req.bio},
                                           #{req.establishDate},
                                           #{req.coverPicture},
                                           #{userId}
    )
    """)
    Recruiter createRecruiterProfile(UUID userId, @Param("req") RecruiterRequest request);

    @ResultMap("baseMapper")
    @Select("""
        UPDATE recruiter_profiles SET company_name =  #{req.companyName},
                                           gender = #{req.gender},
                                           phone_number = #{req.phoneNumber},
                                           industry = #{req.industry},
                                           company_location = #{req.companyLocation},
                                           bio = #{req.bio},
                                           establish_date = #{req.establishDate},
                                           cover_picture = #{req.coverPicture}
    WHERE user_id = #{userId}
    RETURNING *;
    """)
    Recruiter updateRecruiterProfile(UUID userId, @Param("req") RecruiterRequest request);

    @Delete("""
        DELETE FROM recruiter_profiles WHERE user_id = #{userId}
    """)
    void deleteRecruiterProfile(UUID userId);
}
