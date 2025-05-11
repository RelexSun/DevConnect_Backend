package com.kshrd.devconnect_springboot.controller;


import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.base.BaseController;
import com.kshrd.devconnect_springboot.model.dto.request.CommentRequest;
import com.kshrd.devconnect_springboot.model.entity.Comment;
import com.kshrd.devconnect_springboot.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "*")
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentsController extends BaseController {

    private final CommentService commentsService;

    @GetMapping
    public ResponseEntity<ApiResponse>  getAllComments(@RequestParam(defaultValue = "1") Integer page,
                                                       @RequestParam(defaultValue = "10") Integer size) {
       return response(ApiResponse.builder()
               .success(true)
                .message("Comments retrieved successfully")
                .status(HttpStatus.OK)
                .payload(commentsService.getAllComments(page, size))
               .build());
    }

    @GetMapping("topicId/{topicId}")
    public ResponseEntity<ApiResponse> getCommentsById(@PathVariable UUID topicId) {
        Comment entity = commentsService.getCommentsById(topicId);
        return response(ApiResponse.builder()
                .success(true)
                .message("Comments retrieved by id successfully")
                .status(HttpStatus.OK)
                .payload(entity)
                .build());
    }

    @PostMapping("topic/{topicId}")
    public ResponseEntity<ApiResponse> createComments(@RequestBody CommentRequest entity , @PathVariable UUID topicId) {
        Comment service = commentsService.createComments(entity, topicId);
        return response(ApiResponse.builder()
                .success(true)
                .message("Comment have been created successfully")
                .status(HttpStatus.CREATED)
                .payload(service)
                .build());
    }

    @PutMapping("comment/{commentId}")
    public ResponseEntity<ApiResponse> updateComments(@PathVariable UUID commentId, @RequestBody CommentRequest entity) {
        Comment updatedEntity = commentsService.updateComments(commentId,entity);
        return response(ApiResponse.builder()
                .success(true)
                .message("Comment have been updated successfully")
                .status(HttpStatus.OK)
                .payload(updatedEntity)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteComments(@PathVariable UUID id) {
        commentsService.deleteComments(id);
        return response(ApiResponse.builder()
                .success(true)
                .message("Comment have been deleted successfully")
                .status(HttpStatus.OK)
                .payload(null)
                .build());
    }

    @PostMapping("/reply/{commentId}")
    public ResponseEntity<ApiResponse> replyToComment(@RequestBody CommentRequest entity , @PathVariable UUID commentId) {
        Comment service = commentsService.insertReplyComment(entity , commentId);
        return response(ApiResponse.builder()
                .success(true)
                .message("Reply have been created successfully")
                .status(HttpStatus.CREATED)
                .payload(service)
                .build());
    }
}
