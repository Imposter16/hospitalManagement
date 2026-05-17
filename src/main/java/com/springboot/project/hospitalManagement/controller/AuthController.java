package com.springboot.project.hospitalManagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.project.hospitalManagement.dto.LoginRequestDto;
import com.springboot.project.hospitalManagement.service.AuthService;
import com.springboot.project.hospitalManagement.service.Helper.Helper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.springboot.project.hospitalManagement.dto.ForgotPasswordRequestDto;
import com.springboot.project.hospitalManagement.dto.ResetPasswordDto;
import com.springboot.project.hospitalManagement.repository.PatientRepository;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final Helper helper;
    private final PatientRepository patientRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @ModelAttribute LoginRequestDto request) {

        var response = authService.login(request);

        return helper.success(
                response,
                "Login successful",
                200);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @Valid @ModelAttribute ForgotPasswordRequestDto request) {

        authService.forgotPassword(request);

        return helper.success(
                null,
                "Reset password link sent to email",
                200);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @Valid @ModelAttribute ResetPasswordDto request) {

        authService.resetPassword(request);

        return helper.success(
                null,
                "Password reset successful",
                200);
    }
}
