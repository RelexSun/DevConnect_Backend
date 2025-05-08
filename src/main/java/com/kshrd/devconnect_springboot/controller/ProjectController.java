package com.kshrd.devconnect_springboot.controller;

import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.base.BaseController;
import com.kshrd.devconnect_springboot.model.dto.request.JoinProjectRequest;
import com.kshrd.devconnect_springboot.model.dto.request.ProjectRequest;
import com.kshrd.devconnect_springboot.service.ProjectService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
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
    public ResponseEntity<ApiResponse> getAllProjects( @RequestParam(defaultValue = "1") @Positive Integer page,
                                                       @RequestParam(defaultValue = "10") @Positive Integer size) {
        return response(ApiResponse.builder()
                .success(true)
                .message("Get project by id successfully")
                .status(HttpStatus.OK)
                .payload(projectService.getAllProject(page, size))
                .build());
    }

    @GetMapping("/{project-id}")
    public ResponseEntity<ApiResponse> getProjectById(@PathVariable("project-id") @Positive UUID projectId) {
        return response(ApiResponse.builder()
                .success(true)
                .message("Get project by id successfully")
                .status(HttpStatus.OK)
                .payload(projectService.getProjectById(projectId))
                .build());
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse> getAllProjectByUser(@RequestParam(defaultValue = "1") @Positive Integer page,
                                                           @RequestParam(defaultValue = "10") @Positive Integer size) {
        return response(ApiResponse.builder()
                .success(true)
                .message("Get project by id successfully")
                .status(HttpStatus.OK)
                .payload(projectService.getAllProjectByUser(page, size))
                .build());
    }

    @GetMapping("/{project-id}/users")
    public ResponseEntity<ApiResponse> getProjectByIdAndUser(@PathVariable("project-id") @Positive UUID projectId) {
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
    public ResponseEntity<ApiResponse> updateProjectById(@PathVariable("project-id") @Positive UUID projectId,
                                                         @Valid @RequestBody ProjectRequest projectRequest) {
        return response(ApiResponse.builder()
                .success(true)
                .message("Update project successfully")
                .status(HttpStatus.OK)
                .payload(projectService.updateProject(projectId, projectRequest))
                .build());
    }

    // patch endpoint for recruiter to update is open status

    @PostMapping("/join")
    public ResponseEntity<ApiResponse> createJoinProject(@RequestBody JoinProjectRequest joinProjectRequest) {
        return response(ApiResponse.builder()
                .success(true)
                .message("Update project successfully")
                .status(HttpStatus.OK)
//                .payload(projectService.createProject(joinProjectRequest))
                .build());

    }


}
