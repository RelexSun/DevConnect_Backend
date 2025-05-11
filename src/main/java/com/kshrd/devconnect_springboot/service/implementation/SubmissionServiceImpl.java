package com.kshrd.devconnect_springboot.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kshrd.devconnect_springboot.model.dto.request.SubmissionRequest;
import com.kshrd.devconnect_springboot.model.dto.request.SubmitCodeRequest;
import com.kshrd.devconnect_springboot.model.entity.Submission;
import com.kshrd.devconnect_springboot.respository.SubmissionRepository;
import com.kshrd.devconnect_springboot.service.CodeChallengeService;
import com.kshrd.devconnect_springboot.service.SubmissionService;
import com.kshrd.devconnect_springboot.utils.CodeGenerator;
import com.kshrd.devconnect_springboot.utils.CurrentUser;
import com.kshrd.devconnect_springboot.utils.VersionLanguage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final CodeChallengeService codingChallengeRepository;
    private final SubmissionRepository submissionRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String PISTON_URL = "https://emkc.org/api/v2/piston/execute";


    public Submission getSubmissionByDevId(UUID id) {
        return submissionRepository.selectSubmissionById(id);
    }

    public List<Submission> getAllSubmission() {
        return submissionRepository.getAllSubmission();
    }

    public void createSubmission(UUID challengeId, Long timeSubmitted , Integer score) {
        SubmissionRequest entity = new SubmissionRequest();
        entity.setChallengeId(challengeId);
        entity.setDeveloperId(CurrentUser.appUserId);
        entity.setSubmitTime(timeSubmitted);
        entity.setSubmittedAt(LocalDateTime.now());
        entity.setScore(score);
        submissionRepository.insertSubmission(entity);
    }

    public String evaluateStudentCode(String studentCode, UUID codeId) throws JsonProcessingException {
        var challenge = codingChallengeRepository.getCodeChallengeById(codeId);
        var testCases = challenge.getTestCase();

        // Extract header and function name
        CodeGenerator.ExtractedFunction extracted = CodeGenerator.extractParts(challenge.getStarterCode(), challenge.getLanguage());

        // Generate full code with student logic and test runner
        String fullCode = CodeGenerator.generate(
                challenge.getLanguage(),
                extracted.header,
                studentCode,
                extracted.fnName,
                testCases
        );
        System.out.println("Full code to be executed: " + fullCode);

        // Prepare Piston API request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("language", challenge.getLanguage());
        requestBody.put("version", VersionLanguage.getVersion(challenge.getLanguage()));
        requestBody.put("files", List.of(Map.of("content", fullCode)));

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        String response = restTemplate.postForObject(PISTON_URL, requestEntity, String.class);

        if (response == null) {
            throw new RuntimeException("No response from Piston API.");
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);

        String stderr = root.path("run").path("stderr").asText().trim();
        if (!stderr.isEmpty()) {
            return "Compilation/Runtime Error:\n" + stderr;
        }

        String output = root.path("run").path("output").asText().trim();

        List<String> expectedOutputList = testCases.stream()
                .map(tc -> String.valueOf(tc.getExpectedOutput()).trim())
                .toList();

        String[] outputs = output.split("\\r?\\n");

        if (outputs.length != expectedOutputList.size()) {
            return "Mismatch in output count. Got: " + outputs.length + ", Expected: " + expectedOutputList.size();
        }
        for (int i = 0; i < expectedOutputList.size(); i++) {
            if (!outputs[i].equals(expectedOutputList.get(i))) {
                return "Test " + (i + 1) + " failed. Output: " + outputs[i] + ", Expected: " + expectedOutputList.get(i);
            }
        }
        return "Passed";
    }
    public String submitCode(SubmitCodeRequest studentCode, UUID codeId) throws JsonProcessingException {
        String result = evaluateStudentCode(studentCode.getCode(), codeId);
        if ("Passed".equals(result)) {
            createSubmission(codeId, studentCode.getTimeSubmitted(), 100);
        }
        return result;
    }
    public String testStudentCode(String studentCode, UUID codeId) throws JsonProcessingException {
        return evaluateStudentCode(studentCode, codeId);
    }
}