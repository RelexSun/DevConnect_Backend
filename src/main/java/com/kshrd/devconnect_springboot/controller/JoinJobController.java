package com.kshrd.devconnect_springboot.  controller;

import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.base.BaseController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.kshrd.devconnect_springboot.service.JoinJobService;
import com.kshrd.devconnect_springboot.model.entity.JoinJob;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import com.kshrd.devconnect_springboot.model.dto.request.JoinJobRequest;
import org.springframework.http.HttpStatus;
import java.util.UUID;


@RestController
@RequestMapping("/api/joinJob")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class JoinJobController  extends BaseController {

    private final JoinJobService joinJobService;

    @GetMapping
    public ResponseEntity<ApiResponse>  getAllJoinJob() {
         return response(ApiResponse.builder()
                .success(true)
                .message("JoinJob retrieved successfully")
                .status(HttpStatus.OK)
                .payload(joinJobService.getAllJoinJob())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getJoinJobById(@PathVariable UUID id) {
        JoinJob entity = joinJobService.getJoinJobById(id);
         return response(ApiResponse.builder()
                .success(true)
                .message("JoinJob retrieved by id successfully")
                .status(HttpStatus.OK)
                .payload(joinJobService.getJoinJobById(id))
                .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createJoinJob(@RequestBody JoinJobRequest entity) {
        return response(ApiResponse.builder()
                .success(true)
                .message("JoinJob have been created successfully")
                .status(HttpStatus.OK)
                .payload(joinJobService.createJoinJob(entity))
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteJoinJob(@PathVariable UUID id) {
        return response(ApiResponse.builder()
                .success(true)
                .message("JoinJob have been deleted successfully")
                .status(HttpStatus.OK)
                .payload(joinJobService.deleteJoinJob(id))
                .build());
    }

    @PutMapping("/{joinId}")
    public ResponseEntity<ApiResponse> updateIsApprove(@PathVariable UUID joinId, @RequestParam boolean isApprove) {
         return response(ApiResponse.builder()
                .success(true)
                .message("approve developer successfully")
                .status(HttpStatus.OK)
                .payload(joinJobService.updateIsApprove(isApprove , joinId))
                .build());
    }

    @GetMapping("/isApprove")
    public ResponseEntity<ApiResponse> getAllJoinJobByIsApprove(@RequestParam Boolean isApprove) {
         return response(ApiResponse.builder()
                .success(true)
                .message("JoinJob retrieved successfully")
                .status(HttpStatus.OK)
                .payload(joinJobService.getAllJoinJobByIsApprove(isApprove))
                .build());
    }

}
