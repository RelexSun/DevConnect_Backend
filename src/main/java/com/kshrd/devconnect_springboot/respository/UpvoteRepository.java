package com.kshrd.devconnect_springboot.respository;

import com.kshrd.devconnect_springboot.model.entity.Upvote;
import org.apache.ibatis.annotations.*;

import java.util.UUID;

@Mapper
public interface UpvoteRepository {

    // DELETE Upvote
    @Select("""
        DELETE
        FROM upvote
        WHERE comment_id = #{upvote.commentId} AND user_id = #{upvote.userId}
        RETURNING *
        """)
        @Results(id = "BaseResultMap", value = {
                @Result(property = "commentId", column = "comment_id"),
                @Result(property = "userId", column = "user_id")
        })
    Upvote deleteUpvote(@Param("upvote") Upvote entity);

    // INSERT Upvote
    @Select("""
        INSERT INTO upvote
        (comment_id, user_id)
        VALUES
        (
            #{upvote.commentId},
            #{upvote.userId}
        )
        RETURNING *;
        """)
        @ResultMap("BaseResultMap")
   
    Upvote insertUpvote(@Param("upvote") Upvote entity);

    @Select("""
     SELECT COUNT(*) AS total_upvotes FROM upvote
     WHERE comment_id = #{commentId}
    """)
    Integer countUpvote(@Param("commentId") UUID commentId);
}
