package com.kshrd.devconnect_springboot.respository;

import com.kshrd.devconnect_springboot.model.dto.request.TopicRequest;
import com.kshrd.devconnect_springboot.model.entity.Topic;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface TopicRepository {
 
    // GET Topic BY ID
    @Select("""
        SELECT * FROM topics
        WHERE topic_id = #{topicId}
    """)
    @Results(id = "BaseResultMap", value = {
            @Result(property = "topicId", column = "topic_id"),
            @Result(property = "content", column = "content"),
            @Result(property = "postedAt", column = "created_at"),
            @Result(property = "creator", column = "user_id" ,
                    one = @One(select = "com.kshrd.devconnect_springboot.respository.AppUserRepository.getUserById")),
            @Result(property = "comments", column = "topic_id",
                    many = @Many(select = "com.kshrd.devconnect_springboot.respository.CommentRepository.selectCommentsByTopicId"))
    })
    Topic selectTopicsById(UUID topicId);
    
    // DELETE Topic
    @Select("""
        DELETE
        FROM topics
        WHERE topic_id = #{topicId}
        RETURNING *
        """)
        @ResultMap("BaseResultMap")
    Topic deleteTopics(UUID topicId);

    // INSERT Topic
    @Select("""
        INSERT INTO topics
        (content, created_at, user_id)
        VALUES
        (
            #{topics.content},
            #{topics.postedAt},
            #{creatorId}
        )
        RETURNING *;
        """)
        @ResultMap("BaseResultMap")
   
    Topic insertTopics(@Param("topics") TopicRequest entity , UUID creatorId);

    // UPDATE  Topic
    @Select("""
    UPDATE topics
    SET
         content = #{topics.content},
         created_at = #{topics.postedAt},
         user_id = #{creatorId}
    WHERE topic_id = #{id}
    RETURNING *;
    """)
    @ResultMap("BaseResultMap")
    
    Topic updateTopics(UUID id , @Param("topics") TopicRequest entity , UUID creatorId);
    
    // GET ALL Topic
        
    @Select("""
        SELECT * FROM topics
    """)
    @ResultMap("BaseResultMap")
    
    List<Topic> getAllTopics();
}
