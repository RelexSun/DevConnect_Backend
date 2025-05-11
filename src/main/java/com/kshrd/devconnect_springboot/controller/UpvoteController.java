package com.kshrd.devconnect_springboot.controller;

import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.base.BaseController;
import com.kshrd.devconnect_springboot.service.UpvoteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/upvote")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class UpvoteController extends BaseController {

    private final UpvoteService upvotesService;

    @PostMapping("/{commentId}")
    public ResponseEntity<ApiResponse> createUpvote(@PathVariable UUID commentId) {
      return response(ApiResponse.builder()
                .success(true)
                .message("Upvote have been created successfully")
                .status(HttpStatus.CREATED)
                .payload(upvotesService.createUpvote(commentId))
                .build());
    }
    @DeleteMapping("/un-upvote/{commentId}")
    public ResponseEntity<ApiResponse> deleteUpvote(@PathVariable UUID commentId) {
      return response(ApiResponse.builder()
                .success(true)
                .message("Upvote have been deleted successfully")
                .status(HttpStatus.OK)
                .payload(upvotesService.deleteUpvote(commentId))
                .build());
    }
}
