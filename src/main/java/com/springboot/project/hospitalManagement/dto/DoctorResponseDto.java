package com.springboot.project.hospitalManagement.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DoctorResponseDto {

    private Long id;
    private String name;
    private String specilization;
    private String email;

}
