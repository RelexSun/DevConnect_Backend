package com.kshrd.devconnect_springboot.  service.implementation;

import java.util.List;

import com.kshrd.devconnect_springboot.exception.BadRequestException;
import com.kshrd.devconnect_springboot.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kshrd.devconnect_springboot.respository.JobsRepository;
import com.kshrd.devconnect_springboot.service.JobsService;
import com.kshrd.devconnect_springboot.model.entity.Jobs;
import com.kshrd.devconnect_springboot.model.dto.request.JobsRequest;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobsServiceImplementation implements JobsService {
    private final JobsRepository repository;
    UUID jobType;
    @Override
    public Jobs getJobsById(UUID id) {
        return repository.selectJobsById(id);
    }

    @Override
    public List<Jobs> getAllJobs(Integer page, Integer size) {
        return repository.getAllJobs(page, size);
    }

    @Override
    public Jobs createJobs(JobsRequest entity) {
        UUID jobType = jobTypeId(entity.getJobType());
        Jobs job = repository.insertJobs(entity , CurrentUser.appUserId , jobType);
        if (job != null) {
            throw new BadRequestException("Job is not created");
        }
        return job;
    }

    @Override
    public Jobs updateJobs(UUID id, JobsRequest entity) {
        UUID jobType = jobTypeId(entity.getJobType());
        return repository.updateJobs(id, entity , jobType);
    }

    @Override
    public Jobs deleteJobs(UUID id) {
        return repository.deleteJobs(id);
    }

    @Override
    public Jobs updateStatusJobs(UUID id, Boolean status) {
        return repository.updateStatus(id, status);
    }

    @Override
    public List<Jobs> getAllJobsByCreatorId() {

        return repository.selectJobsByCreatorId(CurrentUser.appUserId);
    }

    private UUID jobTypeId(String jobType) {
        if (jobType.equalsIgnoreCase("INTERNSHIP")) {
            return UUID.fromString("686e7d59-5066-4c1e-95c9-d8f2fbd9659a");
        } else if (jobType.equalsIgnoreCase("FULL-TIME")) {
            return UUID.fromString("caed1993-351d-4d4f-8921-e58e51783f7f");
        } else if (jobType.equalsIgnoreCase("PART-TIME")) {
            return UUID.fromString("cdb506b6-1b8e-45ef-8d31-3b8500ca3f6a");
        } else {
            throw new IllegalArgumentException("Invalid job type: FULL-TIME , PART-TIME , INTERNSHIP");
        }
    }
}
