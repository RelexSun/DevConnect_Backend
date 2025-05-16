package com.kshrd.devconnect_springboot.  service;


import java.util.List;
import java.util.UUID;

import com.kshrd.devconnect_springboot.model.dto.response.JoinJobResponse;
import com.kshrd.devconnect_springboot.model.entity.JoinJob;
import com.kshrd.devconnect_springboot.model.dto.request.JoinJobRequest;

public interface JoinJobService {
    JoinJobResponse getJoinJobById(UUID id);
    List<JoinJobResponse> getAllJoinJob();
    JoinJobResponse createJoinJob(JoinJobRequest entity , UUID id);
    JoinJobResponse deleteJoinJob(UUID id);
    JoinJobResponse updateIsApprove(boolean isApprove , UUID joinJobId);
    List<JoinJobResponse> getAllJoinJobByIsApprove(Boolean isApprove);
}
