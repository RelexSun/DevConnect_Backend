package com.kshrd.devconnect_springboot.service.implementation;

import com.kshrd.devconnect_springboot.exception.NotFoundException;
import com.kshrd.devconnect_springboot.model.dto.request.AppUserRequest;
import com.kshrd.devconnect_springboot.model.dto.request.EvaluateDeveloperRequest;
import com.kshrd.devconnect_springboot.model.dto.request.HackathonRequest;
import com.kshrd.devconnect_springboot.model.dto.request.SubmitHackathonRequest;
import com.kshrd.devconnect_springboot.model.entity.AppUser;
import com.kshrd.devconnect_springboot.model.entity.Hackathon;
import com.kshrd.devconnect_springboot.respository.AuthRepository;
import com.kshrd.devconnect_springboot.respository.CertificateRepository;
import com.kshrd.devconnect_springboot.respository.HackathonRepository;
import com.kshrd.devconnect_springboot.service.EmailSenderService;
import com.kshrd.devconnect_springboot.service.HackathonService;
import com.kshrd.devconnect_springboot.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HackathonServiceImplement implements HackathonService {
    private final HackathonRepository hackathonRepository;
    private final CertificateRepository certificateRepository;
    private final EmailSenderService emailSenderService;
    private final AuthRepository authRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public List<Hackathon> getAllHackathons(Integer page, Integer size) {
        return hackathonRepository.getAllHackathons(page, size);
    }

    @Override
    public Hackathon getHackathonById(UUID hackathonId) {
        Hackathon hackathon = hackathonRepository.getHackathonById(hackathonId);
        if (hackathon == null) {
            throw new NotFoundException("Hackathon not found with ID: " + hackathonId);
        }
        return hackathon;
    }

    @Override
    public Hackathon updateHackathonById(UUID hackathonId, HackathonRequest request) {
        Hackathon hackathon = hackathonRepository.updateHackathonById(hackathonId, request);
        if (hackathon == null) {
            throw new NotFoundException("Hackathon not found with ID: " + hackathonId);
        }
        validateHackathonDates(request.getStartDate(), request.getEndDate());
        return hackathon;
    }

    @Override
    public Hackathon createHackathon(HackathonRequest request) {
        validateHackathonDates(request.getStartDate(), request.getEndDate());
        return hackathonRepository.createHackathon(request, CurrentUser.appUserId);
    }

    @Override
    public void deleteHackathonById(UUID hackathonId) {
        Hackathon hackathon = hackathonRepository.getHackathonById(hackathonId);
        if (hackathon == null) {
            throw new NotFoundException("Hackathon not found with ID: " + hackathonId);
        }
        hackathonRepository.deleteHackathonById(hackathonId);
    }

    @Override
    public List<Hackathon> getAllHackathonsByCurrentUser() {
        List<Hackathon> hackathons = hackathonRepository.getAllHackathonsByCurrentUser(CurrentUser.appUserId);

        if (hackathons.isEmpty()) {
            throw new NotFoundException("No hackathons were found for your account. Please create a hackathon or check again later.");
        }

        return hackathons;
    }

    @Override
    public Object joinHackathon(UUID hackathonId) {
        return hackathonRepository.joinHackathon(hackathonId, CurrentUser.appUserId);
    }

    @Override
    public void submitHackathon(UUID hackathonId, SubmitHackathonRequest request) {
        hackathonRepository.submitHackathon(hackathonId, request, CurrentUser.appUserId);
    }

    @SneakyThrows
    @Override
    public void evaluateDeveloper(UUID hackathonId, EvaluateDeveloperRequest request) {
        // Step 1: Update the score
        Integer scores = request.getScore();
        hackathonRepository.evaluateDeveloper(hackathonId, request);

        // Step 2: Get the full score from the hackathon
        Integer fullScore = certificateRepository.getFullScoreByHackathonId(hackathonId);

        // Step 3: Check if fullScore is not null
        if (fullScore != null) {
            String description;
            LocalDateTime issuedDate = LocalDateTime.now();

            // Step 4: Certificate logic based on score
            if (scores >= (fullScore * 0.5) && scores <= fullScore) {
                description = "Certificate of Achievement";
                // Step 5: Insert certificate
                certificateRepository.insertCertificate(
                        description,
                        issuedDate,
                        hackathonId,
                        request.getUserId()
                );
            }
            // Step 6: Get the user's email using their userId
            AppUser appUser = authRepository.getUserById(request.getUserId());
            if (appUser != null && appUser.getEmail() != null && !appUser.getEmail().isEmpty()) {
                String certificateDetails = "You have earned a Certificate of Achievement for your performance in the Hackathon!";
                emailSenderService.sendEmail(appUser.getEmail(), certificateDetails);
                redisTemplate.opsForValue().set(appUser.getEmail(), certificateDetails, Duration.ofMinutes(2));
            }
        }
    }
    private void validateHackathonDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null.");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }

        if (startDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Start date cannot be in the past.");
        }

        if (endDate.isAfter(startDate.plusDays(90))) {
            throw new IllegalArgumentException("Hackathon duration must not exceed 90 days.");
        }
    }


}
