package com.kshrd.devconnect_springboot.model.templeJsonb.resumeInfomation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reference {
    private String name;
    private String phoneNumber;
    private String email;

}