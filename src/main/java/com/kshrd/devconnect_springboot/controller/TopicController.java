package com.kshrd.devconnect_springboot.controller;


import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.base.BaseController;
import com.kshrd.devconnect_springboot.model.dto.request.TopicRequest;
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
    public ResponseEntity<ApiResponse<List<Topic>>> getAllTopics() {
        return response("Topics retrieved successfully",topicsService.getAllTopics());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Topic>> getTopicsById(@PathVariable UUID id) {
        return response("Topics retrieved by id successfully", topicsService.getTopicsById(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Topic>> createTopics(@RequestBody TopicRequest entity) {
        return response("Topics have been created successfully", HttpStatus.CREATED, topicsService.createTopics(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Topic>> updateTopics(@PathVariable UUID id, @RequestBody TopicRequest entity) {
        return response("Topics have been updated successfully", topicsService.updateTopics(id,entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteTopics(@PathVariable UUID id) {
        topicsService.deleteTopics(id);
        return response("Topics have been deleted successfully");

    }
}
