package com.kshrd.devconnect_springboot.controller;


import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.base.BaseController;
import com.kshrd.devconnect_springboot.model.dto.request.TopicRequest;
import com.kshrd.devconnect_springboot.model.dto.response.TopicResponse;
import com.kshrd.devconnect_springboot.model.entity.Topic;
import com.kshrd.devconnect_springboot.service.TopicService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/topics")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TopicController extends BaseController {

    private final TopicService topicsService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TopicResponse>>> getAllTopics() {
        return response("Topics retrieved successfully",topicsService.getAllTopics());
    }

    @GetMapping("/{TopicId}")
    public ResponseEntity<ApiResponse<Topic>> getTopicsById(@PathVariable UUID TopicId) {
        return response("Topics retrieved by id successfully", topicsService.getTopicsById(TopicId));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Topic>> createTopics(@RequestBody TopicRequest entity) {
        return response("Topics have been created successfully", HttpStatus.CREATED, topicsService.createTopics(entity));
    }

    @PutMapping("/{TopicId}")
    public ResponseEntity<ApiResponse<Topic>> updateTopics(@PathVariable UUID TopicId, @RequestBody TopicRequest entity) {
        return response("Topics have been updated successfully", topicsService.updateTopics(TopicId,entity));
    }

    @DeleteMapping("/{TopicId}")
    public ResponseEntity<ApiResponse<Object>> deleteTopics(@PathVariable UUID TopicId) {
        topicsService.deleteTopics(TopicId);
        return response("Topics have been deleted successfully");

    }
}
