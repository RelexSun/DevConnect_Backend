package com.kshrd.devconnect_springboot.model.dto.request;

import com.kshrd.devconnect_springboot.model.templeJsonb.resumeInfomation.ResumeInformation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeRequest {
    @NotBlank(message = "Full name is required")
    private String fullName;
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Email is required")
    @Email
    private String email;
    private Date dob;
    @NotBlank(message = "Position is required")
    private String position;
    @NotBlank(message = "Description is required")
    private String description;
    private ResumeInformation information;
}
