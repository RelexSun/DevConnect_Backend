package com.kshrd.devconnect_springboot.  service;


import java.util.List;
import java.util.UUID;
import com.kshrd.devconnect_springboot.model.entity.DeveloperProfiles;
import com.kshrd.devconnect_springboot.model.dto.request.DeveloperProfilesRequest;

public interface DeveloperProfilesService {
    DeveloperProfiles getDeveloperProfilesByCurrentUser();
    DeveloperProfiles createDeveloperProfiles(DeveloperProfilesRequest entity);
    DeveloperProfiles updateDeveloperProfiles (DeveloperProfilesRequest entity);
    DeveloperProfiles deleteDeveloperProfiles();
}
