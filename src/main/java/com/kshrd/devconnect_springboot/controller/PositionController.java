package com.kshrd.devconnect_springboot.controller;

import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.base.BaseController;
import com.kshrd.devconnect_springboot.service.PositionService;
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
@RequestMapping("api/v1/position")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PositionController extends BaseController {
    private final PositionService positionService;
    @GetMapping
    public ResponseEntity<ApiResponse> getAllPositions() {
        return response(ApiResponse.builder()
                .success(true)
                .message("Get all positions successfully!!!")
                .status(HttpStatus.OK)
                .payload(positionService.getAllPositions())
                .build());
    }

    @GetMapping("/{position-id}")
    public ResponseEntity<ApiResponse> getPositionById(@PathVariable("position-id") UUID positionId) {
        return response(ApiResponse.builder()
                .success(true)
                .message("Get all positions successfully!!!")
                .status(HttpStatus.OK)
                .payload(positionService.getPositionById(positionId))
                .build());
    }
}
