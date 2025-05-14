package com.kshrd.devconnect_springboot.controller;

import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.model.entity.Recruiter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/recruiter-profile")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class RecruiterController {
    @GetMapping
    public ResponseEntity<ApiResponse<Recruiter>> getRecruiterProfile() {
        return null;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Recruiter>> createRecruiterProfile() {
        return null;
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Recruiter>> updateRecruiterProfile() {
        return null;
    }
}
