package com.kshrd.devconnect_springboot.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinProjectRequest {
    private String title;
    private String description;
    private UUID projectId;
    private UUID developerId;
    private UUID positionId;
}
