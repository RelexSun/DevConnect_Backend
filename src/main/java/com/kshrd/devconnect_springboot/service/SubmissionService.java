package com.kshrd.devconnect_springboot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kshrd.devconnect_springboot.model.dto.request.SubmitCodeRequest;

import java.util.List;
import java.util.UUID;

public interface SubmissionService {
    List<String> evaluateStudentCode(String studentCode, UUID codeId) throws JsonProcessingException;
    List<String> submitCode(SubmitCodeRequest studentCode, UUID codeId) throws JsonProcessingException;
    List<String> testStudentCode(SubmitCodeRequest studentCode, UUID codeId) throws JsonProcessingException;
}
