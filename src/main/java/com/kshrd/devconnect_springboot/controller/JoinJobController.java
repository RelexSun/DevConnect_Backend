package com.kshrd.devconnect_springboot.  controller;

import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.base.BaseController;
import com.kshrd.devconnect_springboot.model.dto.response.JoinJobResponse;
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
@RequestMapping("/api/v1/join-job")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class JoinJobController  extends BaseController {

    private final JoinJobService joinJobService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<JoinJobResponse>>> getAllJoinJob() {
        return response("Join job retrieved successfully", joinJobService.getAllJoinJob());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<JoinJobResponse>> getJoinJobById(@PathVariable UUID id) {
        return response("Join job retrieved by id successfully", joinJobService.getJoinJobById(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<JoinJobResponse>> createJoinJob(@RequestBody JoinJobRequest entity , @RequestParam UUID id) {
        return response("Join job have been created successfully", HttpStatus.CREATED, joinJobService.createJoinJob(entity , id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<JoinJobResponse>> deleteJoinJob(@PathVariable UUID id) {
        return response("Join job have been deleted successfully", joinJobService.deleteJoinJob(id));
    }

    @PutMapping("/{joinId}")
    public ResponseEntity<ApiResponse<JoinJobResponse>> updateIsApprove(@PathVariable UUID joinId, @RequestParam boolean isApprove) {
        return response("Approve developer successfully", joinJobService.updateIsApprove(isApprove , joinId));
    }

    @GetMapping("/isApprove")
    public ResponseEntity<ApiResponse<List<JoinJobResponse>>> getAllJoinJobByIsApprove(@RequestParam Boolean isApprove) {
        return response("Join job retrieved successfully", joinJobService.getAllJoinJobByIsApprove(isApprove));
    }

}
