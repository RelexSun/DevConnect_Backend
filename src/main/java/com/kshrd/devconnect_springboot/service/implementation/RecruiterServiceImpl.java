package com.kshrd.devconnect_springboot.service.implementation;

import com.kshrd.devconnect_springboot.model.dto.request.RecruiterRequest;
import com.kshrd.devconnect_springboot.model.entity.Recruiter;
import com.kshrd.devconnect_springboot.respository.RecruiterRepository;
import com.kshrd.devconnect_springboot.service.RecruiterService;
import com.kshrd.devconnect_springboot.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecruiterServiceImpl implements RecruiterService {
    private final RecruiterRepository recruiterRepository;
    @Override
    public Recruiter getRecruiterProfile() {
        return recruiterRepository.getRecruiterProfile(CurrentUser.appUserId);
    }

    @Override
    public Recruiter createRecruiterProfile(RecruiterRequest request) {
        return recruiterRepository.createRecruiterProfile(CurrentUser.appUserId, request);
    }

    @Override
    public Recruiter updateRecruiterProfile(RecruiterRequest request) {
        return recruiterRepository.updateRecruiterProfile(CurrentUser.appUserId, request);
    }

    @Override
    public void deleteRecruiterProfile() {
        recruiterRepository.deleteRecruiterProfile(CurrentUser.appUserId);
    }
}
