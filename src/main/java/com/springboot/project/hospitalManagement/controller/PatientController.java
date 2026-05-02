package com.springboot.project.hospitalManagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.project.hospitalManagement.dto.PatientRequestDto;
import com.springboot.project.hospitalManagement.dto.PatientResponseDto;
import com.springboot.project.hospitalManagement.service.PatientService;
import com.springboot.project.hospitalManagement.service.Helper.Helper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final Helper helper;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> createPatient(
            @ModelAttribute PatientRequestDto request) {

        var patient = patientService.createPatient(request);
        return helper.success(patient, "Patient created successfully", 201);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPatient(@PathVariable Long id) {

        var patient = patientService.getPatientById(id);
        return helper.success(patient, "Patient fetched successfully", 200);
    }

    @GetMapping("/image/{fileName}")
    public ResponseEntity<?> getImage(@PathVariable String fileName) {

        var file = helper.loadFileAsResource(fileName);
        return helper.buildFileResponse(file);
    }

    @PostMapping(value = "/update/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> updatePatient(
            @PathVariable Long id,
            @ModelAttribute PatientRequestDto request) {

        var updatedPatient = patientService.updatePatient(id, request);
        return helper.success(updatedPatient, "Patient updated successfully", 200);
    }
}