package com.kshrd.devconnect_springboot.controller;

import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.base.BaseController;
import com.kshrd.devconnect_springboot.model.dto.request.ProjectRequest;
import com.kshrd.devconnect_springboot.model.entity.JoinProject;
import com.kshrd.devconnect_springboot.service.ProjectService;
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
    public ResponseEntity<ApiResponse> getProjectById(@PathVariable("project-id") UUID projectId) {
        return response(ApiResponse.builder()
                .success(true)
                .message("Get project by id successfully")
                .status(HttpStatus.OK)
                .payload(projectService.getProjectById(projectId))
                .build());
    }

    @GetMapping("/users")
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
    public ResponseEntity<ApiResponse> getProjectByIdAndUser(@PathVariable("project-id") UUID projectId) {
        return response(ApiResponse.builder()
                .success(true)
                .message("Get project by id successfully")
                .status(HttpStatus.OK)
                .payload(projectService.getProjectByIdAndUser(projectId))
                .build());
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createProject(@Valid @RequestBody ProjectRequest projectRequest) {
        return response(ApiResponse.builder()
                .success(true)
                .message("Create project successfully")
                .status(HttpStatus.CREATED)
                .payload(projectService.createProject(projectRequest))
                .build());
    }

    @PutMapping("/{project-id}")
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
    public ResponseEntity<ApiResponse> deleteProjectByIdAndUser(@PathVariable("project-id") UUID projectId) {
        projectService.deleteProject(projectId);
        return response(ApiResponse.builder()
                .success(true)
                .message("Deleted project successfully")
                .status(HttpStatus.OK)
                .build());
    }

    // patch endpoint for recruiter to update is open status
    @PatchMapping("/update-status")
    public ResponseEntity<ApiResponse> updateStatus() {
        return null;
    }

//    patch endpoint for recruiter to update join job approved status
    @PatchMapping("/update-join-status")
    public ResponseEntity<ApiResponse> updateJoinJobStatus() {
        return null;
    }

    // join project reference in project

    @PostMapping("/join")
    public ResponseEntity<ApiResponse> createJoinProject(@RequestBody JoinProject joinProjectRequest) {
        return response(ApiResponse.builder()
                .success(true)
                .message("Update project successfully")
                .status(HttpStatus.OK)
                .payload(projectService.createJoinProject(joinProjectRequest))
                .build());

    }


}
