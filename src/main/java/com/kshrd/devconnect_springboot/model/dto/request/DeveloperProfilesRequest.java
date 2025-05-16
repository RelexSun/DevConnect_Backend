package com.kshrd.devconnect_springboot.  model.dto.request;
    
    

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeveloperProfilesRequest {
    private String address;
    private String coverPicture;
    private String cv;
    private String jobTypeId;
    private String userId;
}
