package com.kshrd.devconnect_springboot.  service;


import java.util.List;
import java.util.UUID;
import com.kshrd.devconnect_springboot.model.entity.JoinJob;
import com.kshrd.devconnect_springboot.model.dto.request.JoinJobRequest;

public interface JoinJobService {
    JoinJob getJoinJobById(UUID id);
    List<JoinJob> getAllJoinJob();
    JoinJob createJoinJob(JoinJobRequest entity);
    JoinJob deleteJoinJob(UUID id);
    JoinJob updateIsApprove(boolean isApprove , UUID joinJobId);
    List<JoinJob> getAllJoinJobByIsApprove(Boolean isApprove);
}
