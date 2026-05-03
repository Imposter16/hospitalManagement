package com.springboot.project.hospitalManagement.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class InsuranceRequestDto {
    private String policyNumber;
    private String provider;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate validUntil;
    private Long patientId;
}
