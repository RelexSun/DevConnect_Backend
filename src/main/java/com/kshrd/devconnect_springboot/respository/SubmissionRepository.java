package com.kshrd.devconnect_springboot.respository;

import com.kshrd.devconnect_springboot.model.dto.request.SubmissionRequest;
import com.kshrd.devconnect_springboot.model.entity.Submission;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface SubmissionRepository {
 
    // GET Submission BY ID
    @Select("""
        SELECT *
        FROM submissions
        WHERE user_id = #{id}
    """)
    @Results(id = "BaseResultMap", value = {
            @Result(property = "submissionId", column = "submission_id"),
            @Result(property = "score", column = "score"),
            @Result(property = "submitTime", column = "submit_time"),
            @Result(property = "challengeId", column = "challenge_id"),
            @Result(property = "developerId", column = "user_id"),
            @Result(property = "submittedAt", column = "submitted_at")
    })
    Submission selectSubmissionById(@Param("id") UUID id);

    // INSERT Submission
    @Select("""
        INSERT INTO submissions
        (score, submit_time, challenge_id, user_id, submitted_at)
        VALUES
        (
            #{submission.score},
            #{submission.submitTime},
            #{submission.challengeId},
            #{submission.developerId},
            #{submission.submittedAt}
        )
        RETURNING *;
        """)
        @ResultMap("BaseResultMap")
    Submission insertSubmission(@Param("submission") SubmissionRequest entity);

    // GET ALL Submission
    @Select("""
        SELECT * FROM submissions
    """)
    @ResultMap("BaseResultMap")
    List<Submission> getAllSubmission();
}
