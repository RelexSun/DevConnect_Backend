package com.kshrd.devconnect_springboot.  service.implementation;

import java.util.List;

import com.kshrd.devconnect_springboot.exception.NotFoundException;
import com.kshrd.devconnect_springboot.respository.JobSkillRepository;
import com.kshrd.devconnect_springboot.respository.SkillRepository;
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
    private final JobSkillRepository jobSkillRepository;
    private final SkillRepository skillRepository;
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
        Jobs inserted = repository.insertJobs(entity, CurrentUser.appUserId);
        List<UUID> skillIds = entity.getSkillId();
        for (UUID skillId : skillIds) {
            if (skillRepository.getSkillById(skillId) == null) {
                throw new NotFoundException("Skill not found with id: " + skillId);
            }
            jobSkillRepository.insertSkillByJobId(inserted.getJobId(), skillId);
        }
        return inserted;
    }

    @Override
    public Jobs updateJobs(UUID id, JobsRequest entity) {
        Jobs updateJobs = repository.updateJobs(id , entity);
        jobSkillRepository.deleteSkillByJobId(updateJobs.getJobId());
        List<UUID> skillIds = entity.getSkillId();
        for (UUID skillId : skillIds) {
            if (skillRepository.getSkillById(skillId) == null) {
                throw new NotFoundException("Skill not found with id: " + skillId);
            }
            jobSkillRepository.insertSkillByJobId(updateJobs.getJobId(), skillId);
        }
        return updateJobs;
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

}
