package com.kshrd.devconnect_springboot.  model.dto.request;
    
    

import java.util.Date;
import java.util.UUID;

import com.kshrd.devconnect_springboot.model.templeJsonb.jobBoard.JobBoard;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobsRequest {
    private String title;
    private String salary;
    private String location;
    private Boolean status;
    private String description;
    private Date postedDate;
    private JobBoard jobBoard;
    private String jobType;
    private Integer pax;
}
