package com.kshrd.devconnect_springboot.controller;

import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.base.BaseController;
import com.kshrd.devconnect_springboot.model.entity.DeveloperBadge;
import com.kshrd.devconnect_springboot.service.BadgeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/badge")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class BadgeController extends BaseController {
     private final BadgeService badgeService;
     @GetMapping
     public ResponseEntity<ApiResponse<DeveloperBadge>> getBadgeCurrentUser() {
         return response("Badge retrieved successfully", badgeService.getBadgeCurrentUser());
     }
}
