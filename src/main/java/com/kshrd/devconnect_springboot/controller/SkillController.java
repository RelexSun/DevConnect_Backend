package com.kshrd.devconnect_springboot.controller;

import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.base.BaseController;
import com.kshrd.devconnect_springboot.model.entity.Skill;
import com.kshrd.devconnect_springboot.service.SkillService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/skills")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class SkillController extends BaseController {
    private final SkillService skillService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Skill>>> getAllSkill() {
        return response("Get all skills successfully!!!", skillService.getAllSkill());
    }

    @GetMapping("{skill-id}")
    public ResponseEntity<ApiResponse<Skill>> getAllSkill(@PathVariable("skill-id") UUID skillId) {
        return response("Get skill by id successfully!!!", skillService.getSkillById(skillId));
    }
}
