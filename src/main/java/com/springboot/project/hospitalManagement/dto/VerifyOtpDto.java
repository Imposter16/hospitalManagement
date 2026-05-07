package com.springboot.project.hospitalManagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerifyOtpDto {

    @NotBlank
    private String email;

    @NotBlank
    private String emailOtp;

    @NotBlank
    private String mobileOtp;
}
