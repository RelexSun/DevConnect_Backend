package com.kshrd.devconnect_springboot.controller;

import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.base.BaseController;
import com.kshrd.devconnect_springboot.model.dto.request.ResumeRequest;
import com.kshrd.devconnect_springboot.model.entity.Resume;
import com.kshrd.devconnect_springboot.service.ResumeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ResumeController extends BaseController {
    private final ResumeService resumesService;
    @GetMapping
    public ResponseEntity<ApiResponse<List<Resume>>> getAllResumes() {
        return response("Resumes retrieved successfully",resumesService.getAllResumes());
        }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Resume>> getResumesById(@PathVariable UUID id) {
        return response("Resumes retrieved by id successfully",resumesService.getResumesById(id));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<Resume>> createResumes(@RequestBody ResumeRequest entity) {
        return response("Resumes created successfully", HttpStatus.CREATED, resumesService.createResumes(entity));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Resume>> updateResumes(@PathVariable UUID id, @RequestBody ResumeRequest entity) {
        return response("Resumes updated successfully",resumesService.updateResumes(id, entity));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Resume>> deleteResumes(@PathVariable UUID id) {
        return response("Resumes deleted successfully", resumesService.deleteResumes(id));
    }
}
