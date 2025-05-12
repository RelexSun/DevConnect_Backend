package com.kshrd.devconnect_springboot.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.base.BaseController;
import com.kshrd.devconnect_springboot.model.dto.request.SubmitCodeRequest;
import com.kshrd.devconnect_springboot.service.SubmissionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/submission")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class SubmissionController extends BaseController {
    private final SubmissionService submissionService;
    @PostMapping("/submitCode/{codeId}")
    public ResponseEntity<ApiResponse> submitStudentCode(@RequestBody @Valid SubmitCodeRequest studentCode , @PathVariable UUID codeId) throws JsonProcessingException {
        String finalCode = submissionService.submitCode(studentCode , codeId);

        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .message("Code submitted successfully")
                .payload(finalCode)
                .build());
    }
    @PostMapping("/testCode/{codeId}")
    public ResponseEntity<ApiResponse> testStudentCode(@RequestBody @Valid SubmitCodeRequest studentCode , @PathVariable UUID codeId) throws JsonProcessingException {
        String finalCode = submissionService.testStudentCode(studentCode , codeId);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .message("Code tested successfully")
                .payload(finalCode)
                .build());
    }

}
