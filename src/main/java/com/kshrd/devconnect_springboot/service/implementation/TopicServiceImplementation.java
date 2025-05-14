package com.kshrd.devconnect_springboot.service.implementation;


import com.kshrd.devconnect_springboot.model.dto.request.TopicRequest;
import com.kshrd.devconnect_springboot.model.entity.Topic;
import com.kshrd.devconnect_springboot.respository.SkillRepository;
import com.kshrd.devconnect_springboot.respository.TopicRepository;
import com.kshrd.devconnect_springboot.service.TopicService;
import com.kshrd.devconnect_springboot.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TopicServiceImplementation implements TopicService {
    private final TopicRepository repository;
    private final SkillRepository skillRepository;

    @Override
    public Topic getTopicsById(UUID id) {
        return repository.selectTopicsById(id);
    }

    @Override
    public List<Topic> getAllTopics() {
        return repository.getAllTopics();
    }

    @Override
    public Topic createTopics(TopicRequest entity) {
        Topic inserted = repository.insertTopics(entity, CurrentUser.appUserId);
        List<UUID> skillIds = entity.getSkills();
        for (UUID skillId : skillIds) {
            repository.insertSkillToTopic(inserted.getTopicId(), skillId);
        }
        return inserted;
    }

    @Override
    public Topic updateTopics(UUID id, TopicRequest entity) {
        return repository.updateTopics(id, entity , CurrentUser.appUserId);
    }
    @Override
    public Topic deleteTopics(UUID id) {
        return repository.deleteTopics(id);
    }
}
