package com.kshrd.devconnect_springboot.model.dto.request;

import com.kshrd.devconnect_springboot.model.enums.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.value.qual.EnumVal;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecruiterRequest {
    @NotNull
    private String companyName;

    @EnumVal()
    private Gender gender;
    private String phoneNumber;
    private String industry;
    private String companyLocation;
    private String bio;
    private LocalDate establishDate;
    private String coverPicture;
}
