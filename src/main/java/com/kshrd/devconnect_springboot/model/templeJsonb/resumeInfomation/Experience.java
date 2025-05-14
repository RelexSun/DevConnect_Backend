package com.kshrd.devconnect_springboot.model.templeJsonb.resumeInfomation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Experience {
    private String companyName;
    private String companyLocation;
    private String position;
    private Integer startYear;
    private Integer endYear;

}