package com.kshrd.devconnect_springboot.  service;


import java.util.List;
import java.util.UUID;
import com.kshrd.devconnect_springboot.model.entity.DeveloperProfiles;
import com.kshrd.devconnect_springboot.model.dto.request.DeveloperProfilesRequest;

public interface DeveloperProfilesService {
    DeveloperProfiles getDeveloperProfilesById(UUID id);
    List<DeveloperProfiles> getAllDeveloperProfiles();
    DeveloperProfiles createDeveloperProfiles(DeveloperProfilesRequest entity);
    DeveloperProfiles updateDeveloperProfiles (UUID id, DeveloperProfilesRequest entity);
    DeveloperProfiles deleteDeveloperProfiles(UUID id);
}
