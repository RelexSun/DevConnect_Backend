package com.kshrd.devconnect_springboot.controller;

import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.base.BaseController;
import com.kshrd.devconnect_springboot.model.dto.request.EvaluateDeveloperRequest;
import com.kshrd.devconnect_springboot.model.dto.request.HackathonRequest;
import com.kshrd.devconnect_springboot.model.dto.request.SubmitHackathonRequest;
import com.kshrd.devconnect_springboot.service.HackathonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/hackathons")
@SecurityRequirement(name = "bearerAuth")
public class HackathonController extends BaseController {
    private final HackathonService hackathonService;

    @GetMapping
    @Operation(summary = "Get all hackathons")
    public ResponseEntity<ApiResponse> getAllHackathons(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return response(ApiResponse.builder()
                .success(true)
                .message("All Hackathons fetched successfully")
                .status(HttpStatus.OK)
                .payload(hackathonService.getAllHackathons(page, size))
                .build());
    }

    @GetMapping("/{hackathon_id}")
    @Operation(summary = "Get a hackathon by ID")
    public ResponseEntity<ApiResponse> getHackathonById(@PathVariable("hackathon_id") UUID hackathonId) {
        return response(ApiResponse.builder()
                .success(true)
                .message("Hackathon ID < " + hackathonId + " > Founded")
                .status(HttpStatus.OK)
                .payload(hackathonService.getHackathonById(hackathonId))
                .build());
    }

    @GetMapping("/recruiter")
    @Operation(summary = "Get hackathons by Current user")
    public ResponseEntity<ApiResponse> getAllHackathonsByCurrentUser() {
        return response(ApiResponse.builder()
                .success(true)
                .message("Hackathons have been successfully fetched")
                .status(HttpStatus.OK)
                .payload(hackathonService.getAllHackathonsByCurrentUser())
                .build());
    }

    @PutMapping("/{hackathon_id}")
    @Operation(summary = "Update a hackathon by ID")
    public ResponseEntity<ApiResponse> updateHackathonById(@PathVariable("hackathon_id") UUID hackathonId, @RequestBody HackathonRequest request) {
        return response(ApiResponse.builder()
                .success(true)
                .message("Hackathon ID " + hackathonId + " Founded")
                .status(HttpStatus.OK)
                .payload(hackathonService.updateHackathonById(hackathonId, request))
                .build());
    }

    @PostMapping
    @Operation(summary = "Create a hackathon")
    public ResponseEntity<ApiResponse> createHackathon(@RequestBody HackathonRequest request) {
        return response(ApiResponse.builder()
                .success(true)
                .message("A Hackathon created successfully")
                .status(HttpStatus.CREATED)
                .payload(hackathonService.createHackathon(request))
                .build());
    }

    @DeleteMapping("/{hackathon_id}")
    @Operation(summary = "Delete a hackathon by ID")
    public ResponseEntity<ApiResponse> deleteHackathonById(@PathVariable("hackathon_id") UUID hackathonId) {
        hackathonService.deleteHackathonById(hackathonId);
        return response(ApiResponse.builder()
                .success(true)
                .message("You Deleted a hackathon with ID << " + hackathonId + " >> successfully")
                .status(HttpStatus.OK)
                .build());
    }

    // join hackathon: required(hackathon_id, developer_id, joined_at: now()) insert the requirement to table join_hackathon and the score and the submission is null;
    @PostMapping("/join_hackathon")
    @Operation(summary = "Join hackathon")
    public ResponseEntity<ApiResponse> joinHackathon(@RequestParam UUID hackathonId) {
        return response(ApiResponse.builder()
                .success(true)
                .message("You joined a hackathon")
                .payload(hackathonService.joinHackathon(hackathonId))
                .status(HttpStatus.CREATED)
                .build());
    }

    //* Submit hackathon : when submit required(hackathon_id, developer_id, submission) update table join_hackathon column submission from null to value in submission
    @PutMapping("/submit_hackathon/{hackathon_id}")
    @Operation(summary = "Submit hackathon")
    public ResponseEntity<ApiResponse> submitHackathon(@PathVariable("hackathon_id") UUID hackathonId, @RequestBody SubmitHackathonRequest request) {
        hackathonService.submitHackathon(hackathonId, request);
        return response(ApiResponse.builder()
                .success(true)
                .message("You submitted successfully")
                .status(HttpStatus.CREATED)
                .build());
    }

    //* Evaluate developer's score required(hackathon_id, developer_id, scores) update table join_hackathon column score from null to value that recruiter evaluated, insert certificate
    @PutMapping("/evaluate_developer/{hackathon_id}")
    @Operation(summary = "Evaluate joined developer")
    public ResponseEntity<ApiResponse> evaluateDeveloper(@PathVariable("hackathon_id") UUID hackathonId,  @RequestBody EvaluateDeveloperRequest request) {
        hackathonService.evaluateDeveloper(hackathonId, request);
        return response(ApiResponse.builder()
                .success(true)
                .message("Developer evaluation completed successfully")
                .status(HttpStatus.CREATED)
                .build());
    }
}
