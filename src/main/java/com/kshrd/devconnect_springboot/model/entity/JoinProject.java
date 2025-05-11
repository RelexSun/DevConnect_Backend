package com.kshrd.devconnect_springboot.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinProject {
    private String title;
    private String description;
    private Boolean isApproved;
    private UUID projectId;
    private List<UUID> positionId;
    private List<UUID> developerId;
}
