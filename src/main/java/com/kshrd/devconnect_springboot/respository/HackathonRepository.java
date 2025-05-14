package com.kshrd.devconnect_springboot.respository;

import com.kshrd.devconnect_springboot.model.dto.request.EvaluateDeveloperRequest;
import com.kshrd.devconnect_springboot.model.dto.request.HackathonRequest;
import com.kshrd.devconnect_springboot.model.dto.request.SubmitHackathonRequest;
import com.kshrd.devconnect_springboot.model.entity.Hackathon;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface HackathonRepository {

    @Results(id = "hackathonMapping", value = {
            @Result(property = "hackathonId", column = "hackathon_id"),
            @Result(property = "startDate", column = "start_at"),
            @Result(property = "endDate", column = "finished_at"),
            @Result(property = "createdDate", column = "created_at"),
            @Result(property = "isAvailable", column = "is_available"),
            @Result(property = "creatorId", column = "user_id", one = @One(select = "com.kshrd.devconnect_springboot.respository.AppUserRepository.getUserResponseById")),    //Recruiter Id
            @Result(property = "fullScores", column = "full_scores"),
            @Result(property = "developerId", column = "developer_id")
    })

    @Select("""
            SELECT * FROM hackathons
            OFFSET #{page} LIMIT #{size}
            """)
    List<Hackathon> getAllHackathons(Integer page, Integer size);

    @ResultMap("hackathonMapping")
    @Select("""
            SELECT * FROM hackathons
            WHERE hackathon_id = #{hackathonId}
            """)
    Hackathon getHackathonById(UUID hackathonId);

    @ResultMap("hackathonMapping")
    @Select("""
            UPDATE hackathons
            SET title = #{req.title},
                description = #{req.description},
                start_at = #{req.startDate},
                finished_at = #{req.endDate},
                created_at = #{req.createdDate},
                is_available = #{req.isAvailable},
                full_scores = #{req.fullScores}
            WHERE hackathon_id = #{hackathonId}
            RETURNING *;
            """)
    Hackathon updateHackathonById(UUID hackathonId, @Param("req") HackathonRequest request);

    @ResultMap("hackathonMapping")
    @Select("""
            INSERT INTO hackathons (title, description, start_at, finished_at, created_at, is_available, user_id, full_scores
            ) VALUES (
                #{req.title},
                #{req.description},
                #{req.startDate},
                #{req.endDate},
                #{req.createdDate},
                #{req.isAvailable},
                #{creatorId},
                #{req.fullScores}
            )
            RETURNING *;
            """)
    Hackathon createHackathon(@Param("req") HackathonRequest request, @Param("creatorId") UUID creatorId);

    @Delete("""
            DELETE FROM hackathons WHERE hackathon_id = #{hackathonId}
            """)
    void deleteHackathonById(UUID hackathonId);

    @ResultMap("hackathonMapping")
    @Select("""
            SELECT * FROM hackathons
            WHERE user_id = #{creatorId}
            """)
    List <Hackathon> getAllHackathonsByCurrentUser(UUID creatorId);

    @Results(id = "joinHackathonMapper", value = {
            @Result(property = "hackathonId", column = "hackathon_id", one = @One(select = "getHackathonById")),
            @Result(property = "userId", column = "user_id", one = @One(select = "com.kshrd.devconnect_springboot.respository.AppUserRepository.getUserById")),
    })
    @Select("""
            INSERT INTO join_hackathons (hackathon_id, user_id)
            VALUES ( #{hackathonId}, #{userId});
            """)
    void joinHackathon(@Param("hackathonId") UUID hackathonId, @Param("userId") UUID userId);

    @Update("""
                UPDATE join_hackathons
                SET submission = #{request.submission}, submitted_at = CURRENT_TIMESTAMP
                WHERE hackathon_id = #{hackathonId} AND user_id = #{userId}
            """)
    void submitHackathon(@Param("hackathonId") UUID hackathonId,
                        @Param("request") SubmitHackathonRequest request,
                        @Param("userId") UUID userId);


    @ResultMap("joinHackathonMapper")
    @Update("""
            UPDATE join_hackathons
            SET score = #{request.score}
            WHERE hackathon_id = #{hackathonId} AND user_id = #{request.userId}
            """)
    void evaluateDeveloper(@Param("hackathonId") UUID hackathonId, @Param("request") EvaluateDeveloperRequest request);
}