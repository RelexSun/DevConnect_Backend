package com.kshrd.devconnect_springboot.  controller;

import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.base.BaseController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import com.kshrd.devconnect_springboot.service.JobsService;
import com.kshrd.devconnect_springboot.model.entity.Jobs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import com.kshrd.devconnect_springboot.model.dto.request.JobsRequest;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/jobs")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class JobsController extends BaseController {

    private final JobsService jobsService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Jobs>>>  getAllJobs(@RequestParam(defaultValue = "1") Integer page,
                                                         @RequestParam(defaultValue = "10") Integer size) {
        return response("Jobs retrieved successfully",jobsService.getAllJobs(page, size));
    }

    @GetMapping("/{JobId}")
    public ResponseEntity<ApiResponse<Jobs>> getJobsById(@PathVariable UUID JobId) {
        return response("Jobs retrieved by id successfully",jobsService.getJobsById(JobId));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Jobs>> createJobs(@RequestBody JobsRequest entity) {
        return response("Jobs have been created successfully", HttpStatus.CREATED, jobsService.createJobs(entity));
    }

    @PutMapping("/{JobId}")
    public ResponseEntity<ApiResponse<Jobs>> updateJobs(@PathVariable UUID JobId, @RequestBody JobsRequest entity) {
        return response("Jobs have been updated successfully", jobsService.updateJobs(JobId , entity));
    }

    @DeleteMapping("/{JobId}")
    public ResponseEntity<ApiResponse<Jobs>> deleteJobs(@PathVariable UUID JobId) {
        return response("Jobs have been deleted successfully", jobsService.deleteJobs(JobId));
    }

    @PutMapping("/job-status/{JobId}")
    public ResponseEntity<ApiResponse<Jobs>> updateStatusJobs(@PathVariable UUID JobId, @RequestParam Boolean status) {
        return response("Jobs have been updated successfully", jobsService.updateStatusJobs(JobId , status));
    }
    @GetMapping("/my-all-job")
    public ResponseEntity<ApiResponse<List<Jobs>>> getAllJobsByCreatorId() {
        return response("Jobs retrieved by creator id successfully",jobsService.getAllJobsByCreatorId());
    }

}
