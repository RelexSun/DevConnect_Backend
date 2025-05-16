package com.kshrd.devconnect_springboot.controller;


import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.base.BaseController;
import com.kshrd.devconnect_springboot.model.dto.request.CommentRequest;
import com.kshrd.devconnect_springboot.model.entity.Comment;
import com.kshrd.devconnect_springboot.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentsController extends BaseController {

    private final CommentService commentsService;

    @PostMapping("/{topicId}")
    public ResponseEntity<ApiResponse<Comment>> createComments(@RequestBody CommentRequest entity , @PathVariable UUID topicId) {
        return response("Comment have been created successfully", HttpStatus.CREATED, commentsService.createComments(entity, topicId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Comment>> updateComments(@PathVariable UUID commentId, @RequestBody CommentRequest entity) {
         return response("Comment have been updated successfully", commentsService.updateComments(commentId,entity));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Object>> deleteComments(@PathVariable UUID commentId) {
        commentsService.deleteComments(commentId);
        return response("Comment have been deleted successfully");
    }

    @PostMapping("/reply/{commentId}")
    public ResponseEntity<ApiResponse<Comment>> replyToComment(@RequestBody CommentRequest entity , @PathVariable UUID commentId) {
        return response("Reply have been created successfully", commentsService.insertReplyComment(entity , commentId));
    }
}
