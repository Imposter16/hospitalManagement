package com.springboot.project.hospitalManagement.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.project.hospitalManagement.dto.InsuranceRequestDto;
import com.springboot.project.hospitalManagement.entity.Insurance;
import com.springboot.project.hospitalManagement.service.Helper.Helper;

import jakarta.servlet.http.HttpServletRequest;

import com.springboot.project.hospitalManagement.service.InsuranceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/insurance")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService insuranceService;
    private final Helper helper;

    @PostMapping
    public ResponseEntity<?> assignInsurance(
            @ModelAttribute InsuranceRequestDto request) {

        var patient = insuranceService.assignInsuranceToPatient(request);

        return helper.success(patient, "Insurance assigned successfully", 201);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInsurance(@PathVariable Long id) {

        var insurance = insuranceService.getInsurance(id);

        return helper.success(insurance, "Insurance fetched successfully", 200);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteInsurance(@PathVariable Long id) {

        insuranceService.deleteInsurance(id);

        return helper.success(null, "Insurance deleted successfully", 200);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateInsurance(
            @PathVariable Long id,
            @ModelAttribute InsuranceRequestDto request) {

        var insurance = insuranceService.updateInsurance(id, request);

        return helper.success(insurance, "Insurance updated successfully", 200);
    }

    @GetMapping
    public ResponseEntity<?> getAllInsurance(HttpServletRequest request) {

        var data = insuranceService.getAllInsurance(request);

        return helper.successWithPagination(
                data.getContent(),
                "Insurance list fetched",
                200,
                Map.of(
                        "currentPage", data.getNumber(),
                        "totalPages", data.getTotalPages(),
                        "totalItems", data.getTotalElements()));
    }
}
