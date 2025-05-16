package com.kshrd.devconnect_springboot.  service.implementation;

import java.util.List;

import com.kshrd.devconnect_springboot.respository.DeveloperProfilesRepository;
import com.kshrd.devconnect_springboot.utils.CurrentUser;
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
    public DeveloperProfiles getDeveloperProfilesByCurrentUser() {
        return repository.selectDeveloperProfilesByUserId(CurrentUser.appUserId);
    }

    @Override
    public DeveloperProfiles createDeveloperProfiles(DeveloperProfilesRequest entity) {
        return repository.insertDeveloperProfiles(entity , CurrentUser.appUserId);
    }

    @Override
    public DeveloperProfiles updateDeveloperProfiles(DeveloperProfilesRequest entity) {
        return repository.updateDeveloperProfiles(CurrentUser.appUserId, entity );
    }

    @Override
    public DeveloperProfiles deleteDeveloperProfiles() {
        return repository.deleteDeveloperProfiles(CurrentUser.appUserId);
    }
}
