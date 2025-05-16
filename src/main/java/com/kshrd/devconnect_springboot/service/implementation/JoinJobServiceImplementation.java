package com.kshrd.devconnect_springboot.  service.implementation;

import java.util.List;

import com.kshrd.devconnect_springboot.exception.BadRequestException;
import com.kshrd.devconnect_springboot.exception.NotFoundException;
import com.kshrd.devconnect_springboot.model.dto.response.JoinJobResponse;
import com.kshrd.devconnect_springboot.model.mapper.JoinJobMapper;
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
    private final JoinJobMapper joinJobMapper;
    @Override
    public JoinJobResponse getJoinJobById(UUID id) {
        if (repository.selectJoinJobById(id) == null) {
            throw new NotFoundException("JoinJob not found with id: " + id);
        }
        return joinJobMapper.toResponse(repository.selectJoinJobById(id));
    }

    @Override
    public List<JoinJobResponse> getAllJoinJob() {
        return joinJobMapper.toResponse(repository.getAllJoinJob(CurrentUser.appUserId));
    }

    @Override
    public JoinJobResponse createJoinJob(JoinJobRequest entity , UUID jobId) {
        if (repository.selectJoinJobById(jobId) == null) {
            throw new NotFoundException("Job not found with id: " + jobId);
        }
        // call to developer repo and get developer cv
        String cv = "img.jng";
        return joinJobMapper.toResponse(repository.insertJoinJob(entity , cv , CurrentUser.appUserId , jobId));
    }

    @Override
    public JoinJobResponse deleteJoinJob(UUID id) {
         if (repository.selectJoinJobById(id) == null) {
            throw new NotFoundException("Join Job not found with id: " + id);
        }
        return joinJobMapper.toResponse(repository.deleteJoinJob(id));
    }

    @Override
    public JoinJobResponse updateIsApprove(boolean isApprove, UUID joinJobId) {
        if (repository.selectJoinJobById(joinJobId) == null) {
            throw new NotFoundException("JoinJob not found with id: " + joinJobId);
        }
        return joinJobMapper.toResponse(repository.updateIsApprove(isApprove, joinJobId));
    }

    @Override
    public List<JoinJobResponse> getAllJoinJobByIsApprove(Boolean isApprove) {
        return joinJobMapper.toResponse(repository.getAllJoinJobByStatus(isApprove));
    }
}
