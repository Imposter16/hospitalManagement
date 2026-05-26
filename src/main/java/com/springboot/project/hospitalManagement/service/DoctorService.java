package com.springboot.project.hospitalManagement.service;

import org.springframework.stereotype.Service;

import com.springboot.project.hospitalManagement.dto.DoctorRequestDto;
import com.springboot.project.hospitalManagement.dto.DoctorResponseDto;
import com.springboot.project.hospitalManagement.entity.Doctor;
import com.springboot.project.hospitalManagement.repository.DoctorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorResponseDto createDoctor(
            DoctorRequestDto request) {

        if (doctorRepository.existsByEmail(request.getEmail())) {

            throw new RuntimeException(
                    "Doctor email already exists");
        }

        Doctor doctor = new Doctor();

        doctor.setName(request.getName());

        doctor.setSpecilization(
                request.getSpecilization());

        doctor.setEmail(request.getEmail());

        doctorRepository.save(doctor);

        return mapToResponse(doctor);
    }

    public DoctorResponseDto getDoctorById(Long id) {

        Doctor doctor = doctorRepository.findById(id)

                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        return mapToResponse(doctor);
    }

    public DoctorResponseDto updateDoctor(
            Long id,
            DoctorRequestDto request) {

        Doctor doctor = doctorRepository.findById(id)

                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        if (request.getName() != null) {
            doctor.setName(request.getName());
        }

        if (request.getSpecilization() != null) {
            doctor.setSpecilization(
                    request.getSpecilization());
        }

        if (request.getEmail() != null) {
            doctor.setEmail(request.getEmail());
        }

        doctorRepository.save(doctor);

        return mapToResponse(doctor);
    }

    public void deleteDoctor(Long id) {

        Doctor doctor = doctorRepository.findById(id)

                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        doctorRepository.delete(doctor);
    }

    private DoctorResponseDto mapToResponse(
            Doctor doctor) {

        return DoctorResponseDto.builder()

                .id(doctor.getId())

                .name(doctor.getName())

                .specilization(
                        doctor.getSpecilization())

                .email(doctor.getEmail())

                .build();
    }
}