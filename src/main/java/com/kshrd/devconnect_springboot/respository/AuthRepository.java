package com.kshrd.devconnect_springboot.respository;
import com.kshrd.devconnect_springboot.config.UuidTypeHandler;
import com.kshrd.devconnect_springboot.model.dto.request.AppUserRequest;
import com.kshrd.devconnect_springboot.model.entity.AppUser;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.UUID;

@Mapper
public interface AuthRepository {
    @Results(id = "authMapper", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "profileImageUrl", column = "profile_image_url"),
            @Result(property = "isRecruiter", column = "is_recruiter"),
            @Result(property = "isVerified", column = "is_verified"),
            @Result(property = "createdAt", column = "created_at")
    })
    @Select("""
        INSERT INTO app_users (first_name, last_name, email, password, profile_image_url, is_recruiter)
                                    VALUES
        (#{req.firstName}, #{req.lastName}, #{req.email}, #{req.password}, #{req.profileImageUrl}, #{req.isRecruiter})
        RETURNING *;
    """)
    AppUser register(@Param("req") AppUserRequest request);

    @ResultMap("authMapper")
    @Select("""
        UPDATE app_users SET password = #{password} WHERE email = #{email}
        RETURNING *;
    """)
    AppUser updatePassword(String email, String password);

    @ResultMap("authMapper")
    @Select("""
        SELECT * FROM app_users WHERE email = #{email}
    """)
    AppUser getUserByEmail(String email);

}
