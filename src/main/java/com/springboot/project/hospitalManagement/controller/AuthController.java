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

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final Helper helper;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @ModelAttribute LoginRequestDto request) {

        var response = authService.login(request);

        return helper.success(
                response,
                "Login successful",
                200);
    }
}
