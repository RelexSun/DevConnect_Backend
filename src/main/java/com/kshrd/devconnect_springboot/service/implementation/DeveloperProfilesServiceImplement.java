package com.kshrd.devconnect_springboot.  service.implementation;

import java.util.List;

import com.kshrd.devconnect_springboot.respository.DeveloperProfilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kshrd.devconnect_springboot.service.DeveloperProfilesService;
import com.kshrd.devconnect_springboot.model.entity.DeveloperProfiles;
import com.kshrd.devconnect_springboot.model.dto.request.DeveloperProfilesRequest;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeveloperProfilesServiceImplement implements DeveloperProfilesService {
    private final DeveloperProfilesRepository repository;

    @Override
    public DeveloperProfiles getDeveloperProfilesById(UUID id) {
        return repository.selectDeveloperProfilesById(id);
    }

    @Override
    public List<DeveloperProfiles> getAllDeveloperProfiles() {
        return repository.getAllDeveloperProfiles();
    }

    @Override
    public DeveloperProfiles createDeveloperProfiles(DeveloperProfilesRequest entity) {
        return repository.insertDeveloperProfiles(entity);
    }

    @Override
    public DeveloperProfiles updateDeveloperProfiles(UUID id, DeveloperProfilesRequest entity) {
        return repository.updateDeveloperProfiles(id, entity);
    }

    @Override
    public DeveloperProfiles deleteDeveloperProfiles(UUID id) {
        return repository.deleteDeveloperProfiles(id);
    }
}
