package com.kshrd.devconnect_springboot.service.implementation;

import java.util.List;

import com.kshrd.devconnect_springboot.model.dto.request.CodeChallengeRequest;
import com.kshrd.devconnect_springboot.model.entity.CodeChallenge;
import com.kshrd.devconnect_springboot.respository.CodeChallengeRepository;
import com.kshrd.devconnect_springboot.service.CodeChallengeService;
import com.kshrd.devconnect_springboot.utils.CurrentUser;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CodeChallengeServiceImpl implements CodeChallengeService {
    private final CodeChallengeRepository repository;

    public CodeChallengeServiceImpl(CodeChallengeRepository repository) {
        this.repository = repository;
    }

    @Override
    public CodeChallenge getCodeChallengeById(UUID id) {
        return repository.selectCodeChallengeById(id);
    }

    @Override
    public List<CodeChallenge> getAllCodeChallenge() {
        return repository.getAllCodeChallenge();
    }

    @Override
    public CodeChallenge createCodeChallenge(CodeChallengeRequest entity) {

        return repository.insertCodeChallenge(entity , CurrentUser.appUserId);
    }

    @Override
    public CodeChallenge updateCodeChallenge(UUID id, CodeChallengeRequest entity) {
        return repository.updateCodeChallenge(id, entity);
    }

    @Override
    public CodeChallenge deleteCodeChallenge(UUID id) {
        return repository.deleteCodeChallenge(id);
    }
}
