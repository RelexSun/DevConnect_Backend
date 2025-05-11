package com.kshrd.devconnect_springboot.  model.entity;
    
    

import java.util.Date;
import java.util.UUID;

import com.kshrd.devconnect_springboot.model.dto.response.AppUserResponse;
import com.kshrd.devconnect_springboot.model.templeJsonb.jobBoard.JobBoard;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Jobs {
       private UUID jobId;
       private String title;
       private String salary;
       private String location;
       private Boolean status;
       private String description;
       private Date postedDate;
       private AppUserResponse creator;
       private JobBoard jobBoard;
       private String jobType;
         private Integer pax;
}
