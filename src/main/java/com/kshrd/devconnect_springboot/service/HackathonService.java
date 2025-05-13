package com.kshrd.devconnect_springboot.service;

import com.kshrd.devconnect_springboot.model.dto.request.EvaluateDeveloperRequest;
import com.kshrd.devconnect_springboot.model.dto.request.HackathonRequest;
import com.kshrd.devconnect_springboot.model.dto.request.SubmitHackathonRequest;
import com.kshrd.devconnect_springboot.model.entity.Hackathon;

import java.util.List;
import java.util.UUID;

public interface HackathonService {

    List<Hackathon> getAllHackathons(Integer page, Integer size);

    Hackathon getHackathonById(UUID hackathonId);

    Hackathon updateHackathonById(UUID hackathonId, HackathonRequest request);

    Hackathon createHackathon(HackathonRequest request);

    void deleteHackathonById(UUID hackathonId);

    List<Hackathon> getAllHackathonsByCurrentUser();

    Object joinHackathon(UUID hackathonId);

    void submitHackathon(UUID hackathonId, SubmitHackathonRequest request);

    void evaluateDeveloper(UUID hackathonId, EvaluateDeveloperRequest request);
}