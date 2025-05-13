package com.kshrd.devconnect_springboot.controller;

import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.base.BaseController;
import com.kshrd.devconnect_springboot.model.dto.request.JoinProjectRequest;
import com.kshrd.devconnect_springboot.model.dto.request.ProjectRequest;
import com.kshrd.devconnect_springboot.model.dto.response.ProjectResponse;
import com.kshrd.devconnect_springboot.model.entity.JoinProject;
import com.kshrd.devconnect_springboot.model.entity.Project;
import com.kshrd.devconnect_springboot.model.entity.ProjectPosition;
import com.kshrd.devconnect_springboot.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/projects")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProjectController extends BaseController {

    private final ProjectService projectService;

    @GetMapping
    @Operation(summary = "Get all projects")
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getAllProjects(@RequestParam(defaultValue = "1") Integer page,
                                                                             @RequestParam(defaultValue = "10") Integer size,
                                                                             @RequestParam String projectName,
                                                                             @RequestParam String skill
                                                       ) {
        return response("Fetched all project successfully", projectService.getAllProject(page, size));
    }

    @GetMapping("/{project-id}")
    @Operation(summary = "Get project by id")
    public ResponseEntity<ApiResponse<Project>> getProjectById(@PathVariable("project-id") UUID projectId) {
        return response("Get project by id successfully", projectService.getProjectById(projectId));
    }

    @GetMapping("/users")
    @Operation(summary = "Get all project by user")
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getAllProjectByUser(@RequestParam(defaultValue = "1") Integer page,
                                                           @RequestParam(defaultValue = "10") Integer size) {
        return response("Get project by id successfully", projectService.getAllProjectByUser(page, size));
    }

    @GetMapping("/users/{project-id}")
    @Operation(summary = "Get project by id and user")
    public ResponseEntity<ApiResponse<Project>> getProjectByIdAndUser(@PathVariable("project-id") UUID projectId) {
        return response("Get project by id successfully", projectService.getProjectByIdAndUser(projectId));
    }

    @PostMapping("/create")
    @Operation(summary = "Create project")
    public ResponseEntity<ApiResponse<Project>> createProject(@Valid @RequestBody ProjectRequest projectRequest) {
        return response("Create project successfully", HttpStatus.CREATED ,projectService.createProject(projectRequest));
    }

    @PutMapping("/{project-id}")
    @Operation(summary = "Update project by id")
    public ResponseEntity<ApiResponse<Project>> updateProjectById(@PathVariable("project-id") UUID projectId,
                                                         @Valid @RequestBody ProjectRequest projectRequest) {
        return response("Update project successfully", projectService.updateProject(projectId, projectRequest));
    }

    @DeleteMapping("/{project-id}")
    @Operation(summary = "Delete project by id and user")
    public ResponseEntity<ApiResponse<Object>> deleteProjectByIdAndUser(@PathVariable("project-id") UUID projectId) {
        projectService.deleteProject(projectId);
        return response("Deleted project successfully");
    }

    @PatchMapping("/update-status/{project-id}")
    @Operation(summary = "Update project status to close")
    public ResponseEntity<ApiResponse<Object>> updateStatusClose(@RequestParam Boolean status, @PathVariable("project-id") UUID projectId) {

        return response("Update project status successfully", projectService.updateProjectStatus(status, projectId));
    }

    @PatchMapping("/update-join-status/{project-id}/{developer-id}")
    @Operation(summary = "Update project status approval")
    public ResponseEntity<ApiResponse<Object>> updateApprovalTrue(@RequestParam Boolean status, @PathVariable("project-id") UUID projectId, @PathVariable("developer-id") UUID developerId) {
        projectService.updateProjectApproval(status, projectId, developerId);
        return response("Update developer join project approval successfully");
    }

    @PostMapping("/join")
    @Operation(summary = "Join project")
    public ResponseEntity<ApiResponse<JoinProject>> createJoinProject(@RequestBody JoinProjectRequest joinProjectRequest) {
        return response("Join project successfully", HttpStatus.CREATED, projectService.createJoinProject(joinProjectRequest));
    }

    @GetMapping("/position/{project-id}")
    @Operation(summary = "Get all position in a project by id")
    public ResponseEntity<ApiResponse<List<ProjectPosition>>> getAllPositionByProjectId(@PathVariable("project-id") UUID projectId) {
        return response("Get position by project successfully", projectService.getAllPositionByProjectId(projectId));
    }

//    filtering projec

}
