package com.kshrd.devconnect_springboot.service;

import com.kshrd.devconnect_springboot.model.dto.request.CommentRequest;
import com.kshrd.devconnect_springboot.model.entity.Comment;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    Comment getCommentsById(UUID id);
    List<Comment> getAllComments(Integer page, Integer size);
    Comment createComments(CommentRequest entity , UUID topicId);
    Comment updateComments (UUID id, CommentRequest entity);
    Comment deleteComments(UUID id);
    Comment insertReplyComment(CommentRequest entity , UUID commentId);
    Comment updateReplyComment(UUID id, CommentRequest entity);

}
