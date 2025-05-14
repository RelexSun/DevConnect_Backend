package com.kshrd.devconnect_springboot.model.templeJsonb.jobBoard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobBoard {
    private List<Requirement> requirements;
    private List<Responsibility> responsibilities;
    private List<Benefit> benefits;
}
