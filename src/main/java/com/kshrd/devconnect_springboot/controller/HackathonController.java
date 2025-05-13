package com.kshrd.devconnect_springboot.controller;

import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.base.BaseController;
import com.kshrd.devconnect_springboot.model.dto.request.HackathonRequest;
import com.kshrd.devconnect_springboot.model.entity.Hackathon;
import com.kshrd.devconnect_springboot.service.HackathonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/hackathons")
@SecurityRequirement(name = "bearerAuth")
public class HackathonController extends BaseController {
    private final HackathonService hackathonService;

    @GetMapping
    @Operation(summary = "Get all hackathons")
    public ResponseEntity<ApiResponse<List<Hackathon>>> getAllHackathons(
            @RequestParam(defaultValue = "1") Long page,
            @RequestParam(defaultValue = "5") Long size) {
        return response("All Hackathons fetched successfully", hackathonService.getAllHackathons(page, size));
    }

    @GetMapping("/{hackathon_id}")
    @Operation(summary = "Get a hackathon by ID")
    public ResponseEntity<ApiResponse<Hackathon>> getHackathonById(@PathVariable("hackathon_id") UUID hackathonId) {
        return response("Hackathon ID " + hackathonId + " Founded", hackathonService.getHackathonById(hackathonId));
    }

    @PutMapping("/{hackathon_id}")
    @Operation(summary = "Update a hackathon by ID")
    public ResponseEntity<ApiResponse<Hackathon>> updateHackathonById(@PathVariable("hackathon_id") UUID hackathonId, @RequestBody HackathonRequest request) {
        return response("Hackathon ID " + hackathonId + " Founded",hackathonService.updateHackathonById(hackathonId, request));
    }

    @PostMapping
    @Operation(summary = "Create a hackathon")
    public ResponseEntity<ApiResponse<Hackathon>> createHackathon(@RequestBody HackathonRequest request){
        return response("A Hackathon created successfully", HttpStatus.CREATED, hackathonService.createHackathon(request));
    }

    @DeleteMapping("/{hackathon_id}")
    @Operation(summary = "Delete a hackathon by ID")
    public ResponseEntity<ApiResponse<Object>> deleteHackathonById(@PathVariable("hackathon_id") UUID hackathonId){
        hackathonService.deleteHackathonById(hackathonId);
        return response("Delete hackathon successfully");
    }
}
