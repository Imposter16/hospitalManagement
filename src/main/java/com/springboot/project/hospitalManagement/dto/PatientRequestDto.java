package com.springboot.project.hospitalManagement.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class PatientRequestDto {
    private String name;
    private String email;
    private Long mobileNumber;
    private String gender;
    private String bloodGroup;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;

    private MultipartFile image;;
}
