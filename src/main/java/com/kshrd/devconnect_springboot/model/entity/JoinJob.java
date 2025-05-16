package com.kshrd.devconnect_springboot.  model.entity;
    
    

import com.kshrd.devconnect_springboot.model.dto.response.AppUserResponse;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinJob {
       private String joinJobId;
       private String title;
       private String description;
       private Boolean isApprove;
       private String cv;
       private Jobs job;
       private AppUserResponse developer;
}
