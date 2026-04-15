package com.springboot.project.hospitalManagement.service;

import org.springframework.stereotype.Service;

import com.springboot.project.hospitalManagement.entity.Patient;
import com.springboot.project.hospitalManagement.repository.PatientRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {

	private final PatientRepository patientRepository;

	@Transactional
	public Patient getPatientById(Long id) {

		Patient p1 = patientRepository.findById(id).orElseThrow();

		Patient p2 = patientRepository.findById(id).orElseThrow();

		System.out.println(p1 == p2);

		p1.setName("Ramesh");

		return p1;

	}

}
