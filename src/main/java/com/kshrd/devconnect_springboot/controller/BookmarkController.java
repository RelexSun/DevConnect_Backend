package com.kshrd.devconnect_springboot.controller;

import com.kshrd.devconnect_springboot.base.ApiResponse;
import com.kshrd.devconnect_springboot.base.BaseController;
import com.kshrd.devconnect_springboot.model.enums.BookmarkEnum;
import com.kshrd.devconnect_springboot.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/bookmark")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class BookmarkController extends BaseController {
    private final BookmarkService bookmarkService;

    @GetMapping
    @Operation(summary = "Get all user's bookmark")
    public ResponseEntity<ApiResponse> getAllBookmarkByUser(
            @RequestParam @Schema(description = "choose target type") BookmarkEnum bookmarkType,
            @RequestParam(defaultValue = "1") @Positive Integer page,
            @RequestParam(defaultValue = "10") @Positive Integer size
            ) {
        return response(ApiResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .message("Get all bookmark")
                .payload(bookmarkService.bookmarkByType(bookmarkType, page, size))
                .build());
    }

    @PostMapping("/create")
    @Operation(summary = "Create bookmark")
    public ResponseEntity<ApiResponse> createProfileBookmark(
            @RequestParam UUID targetId,
            @RequestParam @Schema(description = "choose target type") BookmarkEnum bookmarkType) {
        return response(ApiResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .message("What")
                .payload(bookmarkService.createBookmark(targetId, bookmarkType.toString().toLowerCase()))
                .build());
    }

}
