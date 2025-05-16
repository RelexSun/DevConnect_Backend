package com.kshrd.devconnect_springboot.respository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper
public interface CertificateRepository {

    @Insert("""
                INSERT INTO hackathon_certificate (
                    description, issued_date, hackathon_id, user_id
                )
                VALUES (
                    #{description}, #{issuedDate}, #{hackathonId}, #{userId}
                )
            """)
    void insertCertificate(@Param("description") String description,
                           @Param("issuedDate") LocalDateTime issuedDate,
                           @Param("hackathonId") UUID hackathonId,
                           @Param("userId") UUID userId);

    @Select("""
            SELECT full_scores FROM hackathons WHERE hackathon_id = #{hackathonId}
            """)
    Integer getFullScoreByHackathonId(UUID hackathonId);

}
