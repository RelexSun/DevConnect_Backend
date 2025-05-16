package com.kshrd.devconnect_springboot.model.dto.request;


import com.kshrd.devconnect_springboot.model.entity.Skill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicRequest {
    private String title;
    private String content;
    private List<UUID> skills;
    private LocalDateTime postedAt;
}
