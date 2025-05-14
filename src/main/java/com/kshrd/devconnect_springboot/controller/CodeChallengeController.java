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

    @GetMapping("/{codeId}")
    public ResponseEntity<ApiResponse<CodeChallenge>> getCodeChallengeById(@PathVariable UUID codeId) {
        return response("CodeChallenge retrieved successfully", codeChallengeService.getCodeChallengeById(codeId));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CodeChallenge>> createCodeChallenge(@RequestBody @Valid CodeChallengeRequest entity) {
        return response("CodeChallenge created successfully",
                        HttpStatus.CREATED,
                        codeChallengeService.createCodeChallenge(entity));

    }

    @PutMapping("/{codeId}")
    public ResponseEntity<ApiResponse<CodeChallenge>> updateCodeChallenge(@PathVariable UUID codeId, @RequestBody @Valid CodeChallengeRequest entity) {
        return response("CodeChallenge updated successfully", codeChallengeService.updateCodeChallenge(codeId,entity));
    }

    @DeleteMapping("/{codeId}")
    public ResponseEntity<ApiResponse<CodeChallenge>> deleteCodeChallenge(@PathVariable UUID codeId) {
        return response("CodeChallenge updated successfully", codeChallengeService.deleteCodeChallenge(codeId));
    }

}
