package com.kshrd.devconnect_springboot.  service;


import java.util.List;
import java.util.UUID;
import com.kshrd.devconnect_springboot.model.entity.Jobs;
import com.kshrd.devconnect_springboot.model.dto.request.JobsRequest;

public interface JobsService {
    Jobs getJobsById(UUID id);
    List<Jobs> getAllJobs(Integer page, Integer size);
    Jobs createJobs(JobsRequest entity);
    Jobs updateJobs (UUID id, JobsRequest entity);
    Jobs deleteJobs(UUID id);
    Jobs updateStatusJobs(UUID id, Boolean status);
    List<Jobs> getAllJobsByCreatorId();
}
