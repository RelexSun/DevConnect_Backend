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
import java.util.UUID;


@RestController
@RequestMapping("/api/jobs")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class JobsController extends BaseController {

    private final JobsService jobsService;

    @GetMapping
    public ResponseEntity<ApiResponse>  getAllJobs(@RequestParam(defaultValue = "1") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer size) {
         return response(ApiResponse.builder()
                .success(true)
                .message("Jobs retrieved successfully")
                .status(HttpStatus.OK)
                .payload(jobsService.getAllJobs(page, size))
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getJobsById(@PathVariable UUID id) {
        Jobs entity = jobsService.getJobsById(id);
         return response(ApiResponse.builder()
                .success(true)
                .message("Jobs retrieved by id successfully")
                .status(HttpStatus.OK)
                .payload(jobsService.getJobsById(id))
                .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createJobs(@RequestBody JobsRequest entity) {
        return response(ApiResponse.builder()
                .success(true)
                .message("Jobs have been created successfully")
                .status(HttpStatus.OK)
                .payload(jobsService.createJobs(entity))
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateJobs(@PathVariable UUID id, @RequestBody JobsRequest entity) {
         return response(ApiResponse.builder()
                .success(true)
                .message("Jobs have been updated successfully")
                .status(HttpStatus.OK)
                .payload(jobsService.updateJobs(id , entity))
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteJobs(@PathVariable UUID id) {
        return response(ApiResponse.builder()
                .success(true)
                .message("Jobs have been deleted successfully")
                .status(HttpStatus.OK)
                .payload(jobsService.deleteJobs(id))
                .build());
    }

    @PutMapping("update/status/{id}")
    public ResponseEntity<ApiResponse> updateStatusJobs(@PathVariable UUID id, @RequestParam Boolean status) {
        return response(ApiResponse.builder()
                .success(true)
                .message("Jobs have been updated successfully")
                .status(HttpStatus.OK)
                .payload(jobsService.updateStatusJobs(id , status))
                .build());
    }
    @GetMapping("/get-jobs-by-creator")
    public ResponseEntity<ApiResponse> getAllJobsByCreatorId() {
        return response(ApiResponse.builder()
                .success(true)
                .message("Jobs retrieved by creator id successfully")
                .status(HttpStatus.OK)
                .payload(jobsService.getAllJobsByCreatorId())
                .build());
    }

}
