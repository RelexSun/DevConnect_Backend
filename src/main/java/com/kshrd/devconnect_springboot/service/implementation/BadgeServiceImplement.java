package com.kshrd.devconnect_springboot.service.implementation;

import com.kshrd.devconnect_springboot.model.entity.Badges;
import com.kshrd.devconnect_springboot.model.entity.DeveloperBadge;
import com.kshrd.devconnect_springboot.respository.BadgeRepository;
import com.kshrd.devconnect_springboot.service.BadgeService;
import com.kshrd.devconnect_springboot.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BadgeServiceImplement implements BadgeService {

    private final BadgeRepository badgeRepository;

    @Override
    public DeveloperBadge getBadgeCurrentUser() {
        return badgeRepository.getBadgesByCurrentUser(CurrentUser.appUserId);
    }
}
