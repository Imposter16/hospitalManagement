package com.springboot.project.hospitalManagement.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class PatientRequestDto {
    private String name;
    private String email;
    private Long mobileNumber;
    private String gender;
    private String bloodGroup;

    private MultipartFile image;;
}
