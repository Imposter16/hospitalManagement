package com.springboot.project.hospitalManagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.project.hospitalManagement.service.AuthService;
import com.springboot.project.hospitalManagement.service.Helper.Helper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final Helper helper;
}
