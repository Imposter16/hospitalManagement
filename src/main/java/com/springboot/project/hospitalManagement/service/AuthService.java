package com.springboot.project.hospitalManagement.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.project.hospitalManagement.dto.ForgotPasswordRequestDto;
import com.springboot.project.hospitalManagement.dto.LoginRequestDto;
import com.springboot.project.hospitalManagement.dto.LoginResponseDto;
import com.springboot.project.hospitalManagement.entity.PasswordResetToken;
import com.springboot.project.hospitalManagement.entity.Patient;
import com.springboot.project.hospitalManagement.repository.PasswordResetTokenRepository;
import com.springboot.project.hospitalManagement.repository.PatientRepository;
import java.util.UUID;
import com.springboot.project.hospitalManagement.dto.ResetPasswordDto;
import com.springboot.project.hospitalManagement.service.Helper.Helper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final PasswordResetTokenRepository tokenRepository;
    private final Helper helper;

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

    public void forgotPassword(
            ForgotPasswordRequestDto request) {

        Patient patient = patientRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email not found"));

        if (!patient.getIsEmailVerified()) {
            throw new RuntimeException(
                    "Email is not verified");
        }

        if (!patient.getIsMobileVerified()) {
            throw new RuntimeException(
                    "Mobile number is not verified");
        }

        tokenRepository.deleteByEmail(
                patient.getEmail());

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();

        resetToken.setEmail(patient.getEmail());

        resetToken.setToken(token);

        resetToken.setExpiryTime(
                LocalDateTime.now().plusMinutes(15));

        tokenRepository.save(resetToken);

        // RESET URL

        String resetLink = "http://localhost:8080/api/auth/reset-password?token="
                + token;

        // SEND EMAIL

        helper.sendResetPasswordMail(
                patient.getEmail(),
                patient.getName(),
                resetLink);
    }

    public void resetPassword(
            ResetPasswordDto request) {

        PasswordResetToken resetToken = tokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new RuntimeException(
                        "Invalid token"));

        if (resetToken.getExpiryTime()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException(
                    "Reset token expired");
        }

        if (!request.getPassword()
                .equals(request.getConfirmPassword())) {

            throw new RuntimeException(
                    "Password and confirm password do not match");
        }

        Patient patient = patientRepository
                .findByEmail(resetToken.getEmail())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        patient.setPassword(
                passwordEncoder.encode(
                        request.getPassword()));

        patientRepository.save(patient);

        tokenRepository.delete(resetToken);
    }
}
