package com.kshrd.devconnect_springboot.model.dto.request;

import com.kshrd.devconnect_springboot.model.enums.Gender;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
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

    @NotNull
    private Gender gender;

    @NotNull
    @Pattern(regexp = "^\\+?[1-9]\\d{0,3}(?:[-.\\s]?\\d{1,4}){2,4}$", message = "Invalid phone number")
    private String phoneNumber;

    @NotNull
    private String industry;

    @NotNull
    private String companyLocation;

    @NotNull
    private String bio;

    @NotNull
    @PastOrPresent
    private LocalDate establishDate;

    private String coverPicture;
}
