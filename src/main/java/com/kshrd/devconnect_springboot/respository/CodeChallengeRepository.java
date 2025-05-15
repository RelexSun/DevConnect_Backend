package com.kshrd.devconnect_springboot.respository;
import com.kshrd.devconnect_springboot.config.TestCaseListTypeHandler;
import com.kshrd.devconnect_springboot.model.dto.request.CodeChallengeRequest;
import com.kshrd.devconnect_springboot.model.entity.CodeChallenge;
import org.apache.ibatis.annotations.*;
import java.util.List;

import java.util.UUID;

@Mapper
public interface CodeChallengeRepository {
 
    // GET CodeChallenge BY ID
    @Select("""
        SELECT *
        FROM code_challenge
        WHERE challenge_id = #{id}
    """)
    @Results(id = "BaseResultMap", value = {
            @Result(property = "challengeId", column = "challenge_id"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "testCase", column = "test_case", typeHandler = TestCaseListTypeHandler.class),
            @Result(property = "problemDetail", column = "problem_detail"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "score", column = "score"),
            @Result(property = "creatorId", column = "creator_id"),
            @Result(property = "starterCode", column = "starter_code"),
            @Result(property = "language", column = "language")
    })
    CodeChallenge selectCodeChallengeById(@Param("id") UUID id);

    // DELETE CodeChallenge
    @Select("""
        DELETE 
        FROM code_challenges 
        WHERE challenge_id = #{challengeId}
        RETURNING *
        """)
        @ResultMap("BaseResultMap")
    CodeChallenge deleteCodeChallenge(UUID challengeId);

    // INSERT CodeChallenge
    @Select("""
        INSERT INTO code_challenges
        (title, description, test_case, problem_detail, created_at, score, creator_id, starter_code, language)
        VALUES 
        (
            #{codeChallenge.title},
            #{codeChallenge.description},
            #{codeChallenge.testCase , typeHandler=com.kshrd.devconnect_springboot.config.TestCaseListTypeHandler},
            #{codeChallenge.problemDetail},
            #{codeChallenge.createdAt},
            #{codeChallenge.score},
            #{creatorId},
            #{codeChallenge.starterCode},
            #{codeChallenge.language}
        )
        RETURNING *;
        """)
        @ResultMap("BaseResultMap")
    CodeChallenge insertCodeChallenge(@Param("codeChallenge") CodeChallengeRequest codeChallengeRequest , UUID creatorId);

    // UPDATE  CodeChallenge
    @Select("""
    UPDATE code_challenges
    SET
         title = #{codeChallenge.title},
         description = #{codeChallenge.description},
         test_case = #{codeChallenge.testCase , typeHandler=com.kshrd.devconnect_springboot.config.TestCaseListTypeHandler},
         problem_detail = #{codeChallenge.problemDetail},
         created_at = #{codeChallenge.createdAt},
         score = #{codeChallenge.score},
         starter_code = #{codeChallenge.starterCode},
         language = #{codeChallenge.language}
    WHERE challenge_id = #{id}
    RETURNING *;
    """)
    @ResultMap("BaseResultMap")
    CodeChallenge updateCodeChallenge(UUID id , @Param("codeChallenge") CodeChallengeRequest entity);

    // GET ALL CodeChallenge

    @Select("""
        SELECT * FROM code_challenges
    """)
    @ResultMap("BaseResultMap")

    List<CodeChallenge> getAllCodeChallenge();


}
