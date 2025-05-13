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
@RequestMapping("/api/v1/submission")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class SubmissionController extends BaseController {
    private final SubmissionService submissionService;
    @PostMapping("/submitCode/{codeId}")
    public ResponseEntity<ApiResponse<String>> submitStudentCode(@RequestBody @Valid SubmitCodeRequest studentCode , @PathVariable UUID codeId) throws JsonProcessingException {
        return response("Code submitted successfully", submissionService.submitCode(studentCode , codeId));
    }
    @PostMapping("/testCode/{codeId}")
    public ResponseEntity<ApiResponse<String>> testStudentCode(@RequestBody @Valid String studentCode , @PathVariable UUID codeId) throws JsonProcessingException {
        return response("Code tested successfully",submissionService.testStudentCode(studentCode , codeId));
    }

}
