package com.kshrd.devconnect_springboot.service.implementation;


import com.kshrd.devconnect_springboot.model.dto.request.CommentRequest;
import com.kshrd.devconnect_springboot.model.entity.Comment;
import com.kshrd.devconnect_springboot.respository.CommentRepository;
import com.kshrd.devconnect_springboot.service.CommentService;
import com.kshrd.devconnect_springboot.utils.CurrentUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImplement implements CommentService {
    private final CommentRepository repository;
    public CommentServiceImplement(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Comment getCommentsById(UUID id) {
        return repository.selectCommentsByTopicId(id);
    }

    @Override
    public List<Comment> getAllComments(Integer page, Integer size) {
        return repository.getAllComments(size, page);
    }

    @Override
    public Comment createComments(CommentRequest entity , UUID topicId) {
        return repository.insertComments(entity , CurrentUser.appUserId, topicId);
    }

    @Override
    public Comment updateComments(UUID id, CommentRequest entity) {
        return repository.updateComments(entity,id, CurrentUser.appUserId);
    }

    @Override
    public Comment deleteComments(UUID id) {
        return repository.deleteComments(id);
    }

    @Override
    public Comment insertReplyComment(CommentRequest entity , UUID commentId ) {
        return repository.insertReplyComment(entity , CurrentUser.appUserId , commentId );
    }

    @Override
    public Comment updateReplyComment(UUID id, CommentRequest entity) {
        return null;
    }
}
