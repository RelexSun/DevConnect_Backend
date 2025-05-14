package com.kshrd.devconnect_springboot.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HackathonRequest {

    @NotBlank(message = "Hackathon title cannot be blank")
    @Pattern(regexp = "^(?!\\s*$).+", message = "Hackathon title must contain more than one word")
    private String title;

    @NotBlank(message = "Hackathon description cannot be blank")
    @Pattern(regexp = "^(?!\\s*$).+", message = "Hackathon description must contain more than one word")
    private String description;


    @NotNull(message = "Start date cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime startDate;

    @NotNull(message = "End date cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime endDate;

    @NotNull(message = "Created date cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime createdDate = LocalDateTime.now();

    @NotNull(message = "Availability status cannot be null")
    private Boolean isAvailable;

    @NotNull(message = "Full score cannot be null")
    @Min(value = 50, message = "Full score must be at least 50")
    @Max(value = 100, message = "Full score cannot be more than 100")
    @Pattern(regexp = "^[0-9]+$", message = "Full score must be an integer")
    private Integer fullScores;

}