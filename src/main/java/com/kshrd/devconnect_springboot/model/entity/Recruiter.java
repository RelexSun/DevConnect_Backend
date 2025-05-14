package com.kshrd.devconnect_springboot.model.entity;

import com.kshrd.devconnect_springboot.model.dto.response.AppUserResponse;
import com.kshrd.devconnect_springboot.model.dto.response.UserResponse;
import com.kshrd.devconnect_springboot.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recruiter {
    private UUID recruiterId;
    private String companyName;
    private Gender gender;
    private String phoneNumber;
    private String industry;
    private String companyLocation;
    private String bio;
    private LocalDate establishDate;
    private String coverPicture;
    private AppUserResponse userInformation;
}
