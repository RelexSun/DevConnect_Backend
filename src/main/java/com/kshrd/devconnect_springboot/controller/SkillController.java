package com.kshrd.devconnect_springboot.controller;

import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.base.BaseController;
import com.kshrd.devconnect_springboot.service.SkillService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/skills")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class SkillController extends BaseController {
    private final SkillService skillService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllSkill() {
        return response(ApiResponse.builder()
                .success(true)
                .message("Get all skills successfully!!!")
                .status(HttpStatus.OK)
                .payload(skillService.getAllSkill())
                .build());
    }

    @GetMapping("{skill-id}")
    public ResponseEntity<ApiResponse> getAllSkill(@PathVariable("skill-id") UUID skillId) {
        return response(ApiResponse.builder()
                .success(true)
                .message("Get skill by id successfully!!!")
                .status(HttpStatus.OK)
                .payload(skillService.getSkillById(skillId))
                .build());
    }
}
