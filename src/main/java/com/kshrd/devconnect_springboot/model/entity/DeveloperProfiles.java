package com.kshrd.devconnect_springboot.  model.entity;
    
    

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeveloperProfiles {
       private String developerId;
       private String bio;
       private String address;
       private String coverPicture;
       private String cv;
       private String githubUsername;
       private Integer topComment;
       private Integer mvpCount;
       private Integer topOneCount;
       private Boolean employeeStatus;
       private String jobTypeId;
       private String userId;
}
