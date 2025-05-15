package com.kshrd.devconnect_springboot.service.implementation;

import com.kshrd.devconnect_springboot.model.dto.request.ResumeRequest;
import com.kshrd.devconnect_springboot.model.entity.Resume;
import com.kshrd.devconnect_springboot.respository.ResumeRepository;
import com.kshrd.devconnect_springboot.service.ResumeService;
import com.kshrd.devconnect_springboot.utils.CurrentUser;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository repository;
    public ResumeServiceImpl(ResumeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Resume selectCurrentResumes() {
        return repository.selectCurrentResumes(CurrentUser.appUserId);
    }

    @Override
    public Resume createResumes(ResumeRequest entity) {
        return repository.insertResumes(entity , CurrentUser.appUserId);
    }

    @Override
    public Resume updateResumes(ResumeRequest entity) {

        return repository.updateResumes(entity , CurrentUser.appUserId);
    }

    @Override
    public Resume deleteResumes() {
        return repository.deleteResumes(CurrentUser.appUserId);
    }
}
