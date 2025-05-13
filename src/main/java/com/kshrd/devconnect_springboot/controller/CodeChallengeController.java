package com.kshrd.devconnect_springboot.controller;

import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.base.BaseController;
import com.kshrd.devconnect_springboot.model.dto.request.CodeChallengeRequest;
import com.kshrd.devconnect_springboot.model.entity.CodeChallenge;
import com.kshrd.devconnect_springboot.service.CodeChallengeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/code-challenge")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class CodeChallengeController extends BaseController {

    private final CodeChallengeService codeChallengeService;
    @GetMapping
    public ResponseEntity<ApiResponse<List<CodeChallenge>>>  getAllCodeChallenge() {
        return response("CodeChallenge retrieved successfully", codeChallengeService.getAllCodeChallenge());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CodeChallenge>> getCodeChallengeById(@PathVariable UUID id) {
        return response("CodeChallenge retrieved successfully", codeChallengeService.getCodeChallengeById(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CodeChallenge>> createCodeChallenge(@RequestBody @Valid CodeChallengeRequest entity) {
        return response("CodeChallenge created successfully",
                        HttpStatus.CREATED,
                        codeChallengeService.createCodeChallenge(entity));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CodeChallenge>> updateCodeChallenge(@PathVariable UUID id, @RequestBody @Valid CodeChallengeRequest entity) {
        return response("CodeChallenge updated successfully", codeChallengeService.updateCodeChallenge(id,entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CodeChallenge>> deleteCodeChallenge(@PathVariable UUID id) {
        return response("CodeChallenge updated successfully", codeChallengeService.deleteCodeChallenge(id));
    }

}
