package com.kshrd.devconnect_springboot.  controller;

import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.base.BaseController;
import com.kshrd.devconnect_springboot.model.dto.request.DeveloperProfilesRequest;
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
@RequiredArgsConstructor
public class DeveloperProfilesController extends BaseController {

    private final DeveloperProfilesService developerProfilesService;
// return responseEntity("Leaderboard retrieved successfully", HttpStatus.OK, students);
    @GetMapping
    public ResponseEntity<ApiResponse<List<DeveloperProfiles>>>  getAllDeveloperProfiles() {
        List<DeveloperProfiles> entity =  developerProfilesService.getAllDeveloperProfiles();
        return response("DeveloperProfiles retrieved successfully", entity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DeveloperProfiles>> getDeveloperProfilesById(@PathVariable UUID id) {
        DeveloperProfiles entity = developerProfilesService.getDeveloperProfilesById(id);
        return response("DeveloperProfiles retrieved by id successfully", entity);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DeveloperProfiles>> createDeveloperProfiles(@RequestBody DeveloperProfilesRequest entity) {
        DeveloperProfiles service = developerProfilesService.createDeveloperProfiles(entity);
        return response("DeveloperProfiles have been created successfully", service);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DeveloperProfiles>> updateDeveloperProfiles(@PathVariable UUID id, @RequestBody DeveloperProfilesRequest entity) {
        DeveloperProfiles updatedEntity = developerProfilesService.updateDeveloperProfiles(id,entity);
        return response("DeveloperProfiles have been updated successfully", updatedEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDeveloperProfiles(@PathVariable UUID id) {
        DeveloperProfiles entity = developerProfilesService.deleteDeveloperProfiles(id);
        return response("DeveloperProfiles have been deleted successfully" , null);
    }

}
