package com.springboot.project.hospitalManagement.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.project.hospitalManagement.dto.LoginRequestDto;
import com.springboot.project.hospitalManagement.dto.LoginResponseDto;
import com.springboot.project.hospitalManagement.entity.Patient;
import com.springboot.project.hospitalManagement.repository.PatientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PatientRepository patientRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public LoginResponseDto login(
            LoginRequestDto request) {

        Patient patient = patientRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                patient.getPassword());

        if (!matches) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(
                patient.getEmail());

        return LoginResponseDto.builder()

                .token(token)

                .name(patient.getName())

                .email(patient.getEmail())

                .build();
    }
}
