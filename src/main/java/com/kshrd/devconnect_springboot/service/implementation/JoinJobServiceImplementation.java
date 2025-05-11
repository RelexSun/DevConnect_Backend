package com.kshrd.devconnect_springboot.  service.implementation;

import java.util.List;

import com.kshrd.devconnect_springboot.respository.JoinJobRepository;
import com.kshrd.devconnect_springboot.service.AppUserService;
import com.kshrd.devconnect_springboot.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kshrd.devconnect_springboot.service.JoinJobService;
import com.kshrd.devconnect_springboot.model.entity.JoinJob;
import com.kshrd.devconnect_springboot.model.dto.request.JoinJobRequest;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JoinJobServiceImplementation implements JoinJobService {
    private final JoinJobRepository repository;

    @Override
    public JoinJob getJoinJobById(UUID id) {
        return repository.selectJoinJobById(id);
    }

    @Override
    public List<JoinJob> getAllJoinJob() {
        return repository.getAllJoinJob(CurrentUser.appUserId);
    }

    @Override
    public JoinJob createJoinJob(JoinJobRequest entity) {
        // call to developer repo and get developer cv
        String cv = "img.jng";
        return repository.insertJoinJob(entity , cv , CurrentUser.appUserId);
    }

    @Override
    public JoinJob deleteJoinJob(UUID id) {
        return repository.deleteJoinJob(id);
    }

    @Override
    public JoinJob updateIsApprove(boolean isApprove, UUID joinJobId) {
        return repository.updateIsApprove(isApprove, joinJobId);
    }

    @Override
    public List<JoinJob> getAllJoinJobByIsApprove(Boolean isApprove) {
        return repository.getAllJoinJobByStatus(isApprove);
    }
}
