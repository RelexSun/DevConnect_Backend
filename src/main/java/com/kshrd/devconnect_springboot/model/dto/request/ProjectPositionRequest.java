package com.kshrd.devconnect_springboot.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectPositionRequest {
    private Integer maxMembers;
    private UUID positionId;
}
