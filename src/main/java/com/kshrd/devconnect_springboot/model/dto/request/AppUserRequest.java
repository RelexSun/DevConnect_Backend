package com.kshrd.devconnect_springboot.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRequest {
    @NotBlank
    private String firstName;   

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    private String email;

    private Boolean isRecruiter;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$", message = "Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, and one special character (@#$%^&+=!).")
    private String password;

    private String profileImageUrl;
}
