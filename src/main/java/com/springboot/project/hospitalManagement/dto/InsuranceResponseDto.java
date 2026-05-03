package com.springboot.project.hospitalManagement.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InsuranceResponseDto {

    private Long id;
    private String policyNumber;
    private String provider;
    private LocalDate validUntil;

    private PatientResponseDto patient;
}
