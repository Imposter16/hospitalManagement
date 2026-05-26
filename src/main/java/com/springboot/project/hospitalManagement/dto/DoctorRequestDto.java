package com.springboot.project.hospitalManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Specialization is required")
    private String specilization;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;
}
