package com.kshrd.devconnect_springboot.  model.dto.request;
    
    

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinJobRequest {
    private String title;
    private String description;
    private UUID jobId;
}
