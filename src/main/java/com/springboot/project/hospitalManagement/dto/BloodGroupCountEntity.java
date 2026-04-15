package com.springboot.project.hospitalManagement.dto;

import com.springboot.project.hospitalManagement.entity.BloodGroup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BloodGroupCountEntity {
    private BloodGroup bloodGroupType;
    private Long count;
}
