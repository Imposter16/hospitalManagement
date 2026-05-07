package com.springboot.project.hospitalManagement.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.project.hospitalManagement.dto.PatientRequestDto;
import com.springboot.project.hospitalManagement.dto.PatientResponseDto;
import com.springboot.project.hospitalManagement.dto.VerifyOtpDto;
import com.springboot.project.hospitalManagement.entity.BloodGroup;
import com.springboot.project.hospitalManagement.entity.OtpVerification;
import com.springboot.project.hospitalManagement.entity.Patient;
import com.springboot.project.hospitalManagement.repository.OtpVerificationRepository;
import com.springboot.project.hospitalManagement.repository.PatientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final PatientRepository patientRepository;
    private final OtpVerificationRepository otpRepository;

    // ================= 1. REQUEST OTP =================
    public void requestRegistration(PatientRequestDto request) {

        if (patientRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        if (patientRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new RuntimeException("Mobile already registered");
        }

        String emailOtp = generateOtp();
        String mobileOtp = generateOtp();

        OtpVerification otp = otpRepository.findByEmail(request.getEmail())
                .orElse(new OtpVerification());

        otp.setEmail(request.getEmail());
        otp.setMobileNumber(String.valueOf(request.getMobileNumber()));
        otp.setEmailOtp(emailOtp);
        otp.setMobileOtp(mobileOtp);
        otp.setEmailVerified(false);
        otp.setMobileVerified(false);
        otp.setExpiryTime(LocalDateTime.now().plusMinutes(5));

        otpRepository.save(otp);

        // 🔥 TEMP (replace later with real service)
        System.out.println("Email OTP: " + emailOtp);
        System.out.println("Mobile OTP: " + mobileOtp);
    }

    // ================= 2. VERIFY OTP =================
    public void verifyOtp(VerifyOtpDto request) {

        OtpVerification otp = otpRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (otp.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        if (!otp.getEmailOtp().equals(request.getEmailOtp())) {
            throw new RuntimeException("Invalid email OTP");
        }

        if (!otp.getMobileOtp().equals(request.getMobileOtp())) {
            throw new RuntimeException("Invalid mobile OTP");
        }

        otp.setEmailVerified(true);
        otp.setMobileVerified(true);

        otpRepository.save(otp);
    }

    // ================= 3. COMPLETE REGISTRATION =================
    @Transactional
    public PatientResponseDto completeRegistration(PatientRequestDto request) {

        OtpVerification otp = otpRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (!otp.isEmailVerified() || !otp.isMobileVerified()) {
            throw new RuntimeException("OTP not verified");
        }

        Patient patient = new Patient();

        patient.setName(request.getName());
        patient.setEmail(request.getEmail());
        patient.setMobileNumber(request.getMobileNumber());
        patient.setGender(request.getGender());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setBloodGroup(BloodGroup.valueOf(request.getBloodGroup()));

        patientRepository.save(patient);

        otpRepository.deleteByEmail(request.getEmail());

        return PatientResponseDto.builder()
                .id(patient.getId())
                .name(patient.getName())
                .email(patient.getEmail())
                .mobileNumber(patient.getMobileNumber())
                .gender(patient.getGender())
                .bloodGroup(patient.getBloodGroup().name())
                .build();
    }

    private String generateOtp() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }
}
