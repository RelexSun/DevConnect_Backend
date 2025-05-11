package com.kshrd.devconnect_springboot.controller;

import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.base.BaseController;
import com.kshrd.devconnect_springboot.model.dto.request.JoinProjectRequest;
import com.kshrd.devconnect_springboot.model.dto.request.ProjectRequest;
import com.kshrd.devconnect_springboot.model.entity.JoinProject;
import com.kshrd.devconnect_springboot.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/projects")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProjectController extends BaseController {

    private final ProjectService projectService;

    @GetMapping
    @Operation(summary = "Get all projects")
    public ResponseEntity<ApiResponse> getAllProjects( @RequestParam(defaultValue = "1") Integer page,
                                                       @RequestParam(defaultValue = "10") Integer size) {
        return response(ApiResponse.builder()
                .success(true)
                .message("Get project by id successfully")
                .status(HttpStatus.OK)
                .payload(projectService.getAllProject(page, size))
                .build());
    }

    @GetMapping("/{project-id}")
    @Operation(summary = "Get project by id")
    public ResponseEntity<ApiResponse> getProjectById(@PathVariable("project-id") UUID projectId) {
        return response(ApiResponse.builder()
                .success(true)
                .message("Get project by id successfully")
                .status(HttpStatus.OK)
                .payload(projectService.getProjectById(projectId))
                .build());
    }

    @GetMapping("/users")
    @Operation(summary = "Get all project by user")
    public ResponseEntity<ApiResponse> getAllProjectByUser(@RequestParam(defaultValue = "1") Integer page,
                                                           @RequestParam(defaultValue = "10") Integer size) {
        return response(ApiResponse.builder()
                .success(true)
                .message("Get project by id successfully")
                .status(HttpStatus.OK)
                .payload(projectService.getAllProjectByUser(page, size))
                .build());
    }

    @GetMapping("/users/{project-id}")
    @Operation(summary = "Get project by id and user")
    public ResponseEntity<ApiResponse> getProjectByIdAndUser(@PathVariable("project-id") UUID projectId) {
        return response(ApiResponse.builder()
                .success(true)
                .message("Get project by id successfully")
                .status(HttpStatus.OK)
                .payload(projectService.getProjectByIdAndUser(projectId))
                .build());
    }

    @PostMapping("/create")
    @Operation(summary = "Create project")
    public ResponseEntity<ApiResponse> createProject(@Valid @RequestBody ProjectRequest projectRequest) {
        return response(ApiResponse.builder()
                .success(true)
                .message("Create project successfully")
                .status(HttpStatus.CREATED)
                .payload(projectService.createProject(projectRequest))
                .build());
    }

    @PutMapping("/{project-id}")
    @Operation(summary = "Update project by id")
    public ResponseEntity<ApiResponse> updateProjectById(@PathVariable("project-id") UUID projectId,
                                                         @Valid @RequestBody ProjectRequest projectRequest) {
        return response(ApiResponse.builder()
                .success(true)
                .message("Update project successfully")
                .status(HttpStatus.OK)
                .payload(projectService.updateProject(projectId, projectRequest))
                .build());
    }

    @DeleteMapping("/{project-id}")
    @Operation(summary = "Delete project by id and user")
    public ResponseEntity<ApiResponse> deleteProjectByIdAndUser(@PathVariable("project-id") UUID projectId) {
        projectService.deleteProject(projectId);
        return response(ApiResponse.builder()
                .success(true)
                .message("Deleted project successfully")
                .status(HttpStatus.OK)
                .build());
    }

    @PatchMapping("/update-status-close/{project-id}")
    @Operation(summary = "Update project status to close")
    public ResponseEntity<ApiResponse> updateStatusClose(@PathVariable("project-id") UUID projectId) {
        projectService.updateProjectStatusClose(projectId);
        return response(ApiResponse.builder()
                .success(true)
                .message("Close project successfully")
                .status(HttpStatus.OK)
                .build());
    }

    @PatchMapping("/update-status-open/{project-id}")
    @Operation(summary = "Update project status to open")
    public ResponseEntity<ApiResponse> updateStatusOpen(@PathVariable("project-id") UUID projectId) {
        projectService.updateProjectStatusOpen(projectId);
        return response(ApiResponse.builder()
                .success(true)
                .message("Open project successfully")
                .status(HttpStatus.OK)
                .build());
    }

    @PatchMapping("/update-join-status-approved/{project-id}/{developer-id}")
    @Operation(summary = "Update project status approval")
    public ResponseEntity<ApiResponse> updateApprovalTrue(@PathVariable("project-id") UUID projectId, @PathVariable("developer-id") UUID developerId) {
        projectService.updateApprovalTrue(projectId, developerId);
        return response(ApiResponse.builder()
                .success(true)
                .message("Approved developer successfully")
                .status(HttpStatus.OK)
                .build());
    }

    @PatchMapping("/update-join-status-deny/{project-id}/{developer-id}")
    @Operation(summary = "Update project status approval")
    public ResponseEntity<ApiResponse> updateApprovalClose(@PathVariable("project-id") UUID projectId, @PathVariable("developer-id") UUID developerId) {
        projectService.updateApprovalClose(projectId, developerId);
        return response(ApiResponse.builder()
                .success(true)
                .message("Remove developer from project successfully")
                .status(HttpStatus.OK)
                .build());
    }

    @PostMapping("/join")
    @Operation(summary = "Join project")
    public ResponseEntity<ApiResponse> createJoinProject(@RequestBody JoinProjectRequest joinProjectRequest) {
        return response(ApiResponse.builder()
                .success(true)
                .message("Join project successfully")
                .status(HttpStatus.OK)
                .payload(projectService.createJoinProject(joinProjectRequest))
                .build());
    }

    @GetMapping("/position/{project-id}")
    @Operation(summary = "Get all position in a project by id")
    public ResponseEntity<ApiResponse> getAllPositionByProjectId(@PathVariable("project-id") UUID projectId) {
        return response(ApiResponse.builder()
                .success(true)
                .message("Get position by project successfully")
                .status(HttpStatus.OK)
                .payload(projectService.getAllPositionByProjectId(projectId))
                .build());
    }

}
