package com.springboot.project.hospitalManagement.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import com.springboot.project.hospitalManagement.dto.DoctorRequestDto;
import com.springboot.project.hospitalManagement.service.DoctorService;
import com.springboot.project.hospitalManagement.service.Helper.Helper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    private final Helper helper;

    @PostMapping
    public ResponseEntity<?> createDoctor(

            @Valid @ModelAttribute DoctorRequestDto request,

            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            Map<String, String> errors = new HashMap<>();

            bindingResult.getFieldErrors().forEach(error ->

            errors.put(
                    error.getField(),
                    error.getDefaultMessage()));

            return ResponseEntity.badRequest().body(

                    Map.of(
                            "status", false,
                            "message", "Validation failed",
                            "data", errors));
        }

        var response = doctorService.createDoctor(request);

        return helper.success(
                response,
                "Doctor created successfully",
                201);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctor(
            @PathVariable Long id) {

        var response = doctorService.getDoctorById(id);

        return helper.success(
                response,
                "Doctor fetched successfully",
                200);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDoctor(

            @PathVariable Long id,

            @Valid @RequestBody DoctorRequestDto request,

            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            Map<String, String> errors = new HashMap<>();

            bindingResult.getFieldErrors().forEach(error ->

            errors.put(
                    error.getField(),
                    error.getDefaultMessage()));

            return ResponseEntity.badRequest().body(

                    Map.of(
                            "status", false,
                            "message", "Validation failed",
                            "data", errors));
        }

        var response = doctorService.updateDoctor(
                id,
                request);

        return helper.success(
                response,
                "Doctor updated successfully",
                200);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctor(
            @PathVariable Long id) {

        doctorService.deleteDoctor(id);

        return helper.success(
                null,
                "Doctor deleted successfully",
                200);
    }
}
