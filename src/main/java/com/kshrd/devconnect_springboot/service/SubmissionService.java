package com.kshrd.devconnect_springboot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kshrd.devconnect_springboot.model.dto.request.SubmitCodeRequest;

import java.util.UUID;

public interface SubmissionService {
    String evaluateStudentCode(String studentCode, UUID codeId) throws JsonProcessingException;
    String submitCode(SubmitCodeRequest studentCode, UUID codeId) throws JsonProcessingException;
    String testStudentCode(SubmitCodeRequest studentCode, UUID codeId) throws JsonProcessingException;
}
