package com.kshrd.devconnect_springboot.  controller;

import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.base.BaseController;
import com.kshrd.devconnect_springboot.model.dto.request.DeveloperProfilesRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.kshrd.devconnect_springboot.service.DeveloperProfilesService;
import com.kshrd.devconnect_springboot.model.entity.DeveloperProfiles;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.UUID;

@RestController
@RequestMapping("/api/developerProfiles")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class DeveloperProfilesController extends BaseController {

    private final DeveloperProfilesService developerProfilesService;

    @GetMapping()
    public ResponseEntity<ApiResponse<DeveloperProfiles>> getCurrentProfileDev() {
        DeveloperProfiles entity = developerProfilesService.getDeveloperProfilesByCurrentUser();
        return response("DeveloperProfiles retrieved by id successfully", entity);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DeveloperProfiles>> createDeveloperProfiles(@RequestBody DeveloperProfilesRequest entity) {
        DeveloperProfiles service = developerProfilesService.createDeveloperProfiles(entity);
        return response("DeveloperProfiles have been created successfully", service);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<DeveloperProfiles>> updateDeveloperProfiles(@RequestBody DeveloperProfilesRequest entity) {
        DeveloperProfiles updatedEntity = developerProfilesService.updateDeveloperProfiles(entity);
        return response("DeveloperProfiles have been updated successfully", updatedEntity);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteDeveloperProfiles() {
        DeveloperProfiles entity = developerProfilesService.deleteDeveloperProfiles();
        return response("DeveloperProfiles have been deleted successfully" , null);
    }

}
