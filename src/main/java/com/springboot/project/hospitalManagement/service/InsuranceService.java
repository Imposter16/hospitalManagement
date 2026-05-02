package com.springboot.project.hospitalManagement.service;

import org.springframework.stereotype.Service;

import com.springboot.project.hospitalManagement.entity.Insurance;
import com.springboot.project.hospitalManagement.entity.Patient;
import com.springboot.project.hospitalManagement.repository.InsuranceRepository;
import com.springboot.project.hospitalManagement.repository.PatientRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class InsuranceService {

	private final InsuranceRepository insuranceRepository;
	private final PatientRepository patientRepository;

	@Transactional
	public Patient assignInsuranceToPatient(Insurance insurance, Long patientId) {

		Patient patient = patientRepository.findById(patientId)
				.orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + patientId));

		// Set both sides (VERY IMPORTANT)
		patient.setInsurance(insurance);
		insurance.setPatient(patient);

		// Explicit save (best practice)
		return patientRepository.save(patient);
	}

}
