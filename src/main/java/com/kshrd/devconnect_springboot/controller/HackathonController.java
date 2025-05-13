package com.kshrd.devconnect_springboot.controller;

import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.base.BaseController;
import com.kshrd.devconnect_springboot.model.dto.request.EvaluateDeveloperRequest;
import com.kshrd.devconnect_springboot.model.dto.request.HackathonRequest;
import com.kshrd.devconnect_springboot.model.entity.Hackathon;
import com.kshrd.devconnect_springboot.model.dto.request.SubmitHackathonRequest;
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
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return response("All Hackathons fetched successfully", hackathonService.getAllHackathons(page, size));
    }

    @GetMapping("/{hackathon_id}")
    @Operation(summary = "Get a hackathon by ID")
    public ResponseEntity<ApiResponse<Hackathon>> getHackathonById(@PathVariable("hackathon_id") UUID hackathonId) {
        return response("Hackathon ID < " + hackathonId + " > Founded", hackathonService.getHackathonById(hackathonId));
    }

    @GetMapping("/recruiter")
    @Operation(summary = "Get hackathons by Current user")
    public ResponseEntity<ApiResponse<List<Hackathon>>> getAllHackathonsByCurrentUser() {
        return response("Hackathons have been successfully fetched", hackathonService.getAllHackathonsByCurrentUser());
    }

    @PutMapping("/{hackathon_id}")
    @Operation(summary = "Update a hackathon by ID")
    public ResponseEntity<ApiResponse<Hackathon>> updateHackathonById(@PathVariable("hackathon_id") UUID hackathonId, @RequestBody HackathonRequest request) {
        return response("Hackathon ID " + hackathonId + " Founded", hackathonService.updateHackathonById(hackathonId, request));
    }

    @PostMapping
    @Operation(summary = "Create a hackathon")
    public ResponseEntity<ApiResponse<Hackathon>> createHackathon(@RequestBody HackathonRequest request) {
        return response("A Hackathon created successfully", HttpStatus.CREATED,hackathonService.createHackathon(request));
    }

    @DeleteMapping("/{hackathon_id}")
    @Operation(summary = "Delete a hackathon by ID")
    public ResponseEntity<ApiResponse<Object>> deleteHackathonById(@PathVariable("hackathon_id") UUID hackathonId) {
        hackathonService.deleteHackathonById(hackathonId);
        return response("You Deleted a hackathon with ID << " + hackathonId + " >> successfully");
    }

    // join hackathon: required(hackathon_id, developer_id, joined_at: now()) insert the requirement to table join_hackathon and the score and the submission is null;
    @PostMapping("/join_hackathon")
    @Operation(summary = "Join hackathon")
    public ResponseEntity<ApiResponse<Object>> joinHackathon(@RequestParam UUID hackathonId) {
        return response("You joined a hackathon", HttpStatus.CREATED, hackathonService.joinHackathon(hackathonId));
    }

    //* Submit hackathon : when submit required(hackathon_id, developer_id, submission) update table join_hackathon column submission from null to value in submission
    @PutMapping("/submit_hackathon/{hackathon_id}")
    @Operation(summary = "Submit hackathon")
    public ResponseEntity<ApiResponse> submitHackathon(@PathVariable("hackathon_id") UUID hackathonId, @RequestBody SubmitHackathonRequest request) {
        return response("You submitted successfully", HttpStatus.CREATED, hackathonService.submitHackathon(hackathonId, request));
    }

    //* Evaluate developer's score required(hackathon_id, developer_id, scores) update table join_hackathon column score from null to value that recruiter evaluated, insert certificate
    @PutMapping("/evaluate_developer/{hackathon_id}")
    @Operation(summary = "Evaluate joined developer")
    public ResponseEntity<ApiResponse> evaluateDeveloper(@PathVariable("hackathon_id") UUID hackathonId,  @RequestBody EvaluateDeveloperRequest request) {
        return response("Developer evaluation completed successfully", HttpStatus.CREATED, hackathonService.evaluateDeveloper(hackathonId, request));
    }
}
