package com.kshrd.devconnect_springboot.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {

//    this is for no payload
    protected ResponseEntity<ApiResponse<Object>> response(String message) {
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .message(message)
                .build());
    }

//    this is for http ok mostly just use for get
    protected <T> ResponseEntity<ApiResponse<T>> response(String message, T payload) {
        ApiResponse<T> apiResponse = ApiResponse.<T>builder()
                .success(true)
                .status(HttpStatus.OK)
                .message(message)
                .payload(payload)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

//    this is for create
    protected <T> ResponseEntity<ApiResponse<T>> response(String message, HttpStatus httpStatus, T payload) {
        ApiResponse<T> apiResponse = ApiResponse.<T>builder()
                .success(true)
                .status(httpStatus)
                .message(message)
                .payload(payload)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

}
