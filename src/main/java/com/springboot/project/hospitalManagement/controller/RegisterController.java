package com.springboot.project.hospitalManagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.project.hospitalManagement.dto.PatientRequestDto;
import com.springboot.project.hospitalManagement.dto.VerifyOtpDto;
import com.springboot.project.hospitalManagement.service.RegisterService;
import com.springboot.project.hospitalManagement.service.Helper.Helper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth/register")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;
    private final Helper helper;

    @PostMapping("/request")
    public ResponseEntity<?> requestRegister(
            @Valid @ModelAttribute PatientRequestDto request) {

        registerService.requestRegistration(request);
        return helper.success(null, "OTP sent successfully", 200);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(
            @Valid @ModelAttribute VerifyOtpDto request) {

        registerService.verifyOtp(request);
        return helper.success(null, "OTP verified successfully", 200);
    }

    @PostMapping("/complete")
    public ResponseEntity<?> completeRegistration(
            @Valid @ModelAttribute PatientRequestDto request) {

        var response = registerService.completeRegistration(request);
        return helper.success(response, "Registration successful", 201);
    }
}
