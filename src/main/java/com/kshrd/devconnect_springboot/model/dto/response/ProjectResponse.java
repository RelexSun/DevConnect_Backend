package com.kshrd.devconnect_springboot.model.dto.response;

import com.kshrd.devconnect_springboot.model.entity.Skill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponse {
    private UUID projectId;
    private String title;
    private String description;
    private Boolean isOpen;
    private LocalDate createdAt;
    private UserResponse owner;
    private List<Skill> skills;
}
