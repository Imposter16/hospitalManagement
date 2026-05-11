package com.springboot.project.hospitalManagement.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;

    private String password;

    private String mobileNumber;

    private String otp;
}
