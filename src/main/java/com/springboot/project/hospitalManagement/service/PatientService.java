package com.springboot.project.hospitalManagement.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.project.hospitalManagement.dto.PatientRequestDto;
import com.springboot.project.hospitalManagement.dto.PatientResponseDto;
import com.springboot.project.hospitalManagement.entity.BloodGroup;
import com.springboot.project.hospitalManagement.entity.Patient;
import com.springboot.project.hospitalManagement.repository.PatientRepository;
import com.springboot.project.hospitalManagement.service.Helper.Helper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {

	private final PatientRepository patientRepository;
	private final Helper helper;
	private final BCryptPasswordEncoder passwordEncoder;

	// @Transactional
	// public Patient getPatientById(Long id) {
	//
	// Patient p1 = patientRepository.findById(id).orElseThrow();
	//
	// Patient p2 = patientRepository.findById(id).orElseThrow();
	//
	// System.out.println(p1 == p2);
	//
	// p1.setName("Ramesh");
	//
	// return p1;
	//
	// }

	public Patient createPatient(PatientRequestDto request) {

		Patient patient = new Patient();

		patient.setName(request.getName());
		patient.setEmail(request.getEmail());
		patient.setMobileNumber(request.getMobileNumber());
		patient.setGender(request.getGender());
		if (request.getDateOfBirth() != null) {
			patient.setDateOfBirth(request.getDateOfBirth());
		}

		patient.setBloodGroup(BloodGroup.valueOf(request.getBloodGroup()));

		if (request.getImage() != null && !request.getImage().isEmpty()) {
			String fileName = helper.uploadFile(request.getImage());
			patient.setPatientImage(fileName);
		}

		return patientRepository.save(patient);
	}

	@Transactional
	public PatientResponseDto getPatientById(Long id) {

		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Patient not found"));

		String imageUrl = null;

		if (patient.getPatientImage() != null && !patient.getPatientImage().isBlank()) {
			imageUrl = helper.buildFileUrl(patient.getPatientImage());
		}

		return PatientResponseDto.builder()
				.id(patient.getId())
				.name(patient.getName())
				.email(patient.getEmail())
				.mobileNumber(patient.getMobileNumber())
				.gender(patient.getGender())
				.bloodGroup(
						patient.getBloodGroup() != null ? patient.getBloodGroup().name() : null)
				.dateOfBirth(patient.getDateOfBirth())
				.createdDate(patient.getCreatedDate())
				.imageUrl(imageUrl)
				.build();
	}

	@Transactional
	public PatientResponseDto updatePatient(Long id, PatientRequestDto request) {

		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Patient not found"));

		if (request.getName() != null)
			patient.setName(request.getName());

		if (request.getEmail() != null)
			patient.setEmail(request.getEmail());

		if (request.getMobileNumber() != null)
			patient.setMobileNumber(request.getMobileNumber());

		if (request.getGender() != null)
			patient.setGender(request.getGender());

		if (request.getBloodGroup() != null)
			patient.setBloodGroup(BloodGroup.valueOf(request.getBloodGroup()));

		if (request.getDateOfBirth() != null) {
			patient.setDateOfBirth(request.getDateOfBirth());
		}

		if (request.getImage() != null && !request.getImage().isEmpty()) {

			String newFileName = helper.updateFile(
					patient.getPatientImage(),
					request.getImage());

			patient.setPatientImage(newFileName);
		}

		patientRepository.save(patient);

		String imageUrl = null;
		if (patient.getPatientImage() != null && !patient.getPatientImage().isBlank()) {
			imageUrl = helper.buildFileUrl(patient.getPatientImage());
		}

		return PatientResponseDto.builder()
				.id(patient.getId())
				.name(patient.getName())
				.email(patient.getEmail())
				.mobileNumber(patient.getMobileNumber())
				.gender(patient.getGender())
				.bloodGroup(
						patient.getBloodGroup() != null ? patient.getBloodGroup().name() : null)
				.dateOfBirth(patient.getDateOfBirth())
				.createdDate(patient.getCreatedDate())
				.imageUrl(imageUrl)
				.build();
	}

	@Transactional
	public void deletePatient(Long id) {

		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Patient not found"));

		if (patient.getPatientImage() != null && !patient.getPatientImage().isBlank()) {
			helper.deleteFile(patient.getPatientImage());
		}

		patientRepository.delete(patient);
	}

}
