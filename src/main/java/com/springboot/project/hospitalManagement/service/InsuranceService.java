package com.springboot.project.hospitalManagement.service;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import com.springboot.project.hospitalManagement.dto.*;
import com.springboot.project.hospitalManagement.entity.*;
import com.springboot.project.hospitalManagement.repository.*;
import com.springboot.project.hospitalManagement.service.Helper.Helper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InsuranceService {

	private final InsuranceRepository insuranceRepository;
	private final PatientRepository patientRepository;
	private final Helper helper;

	// ================= CREATE =================
	@Transactional
	public Patient assignInsuranceToPatient(InsuranceRequestDto request) {

		Patient patient = patientRepository.findById(request.getPatientId())
				.orElseThrow(() -> new RuntimeException("Patient not found"));

		if (patient.getInsurance() != null) {
			throw new RuntimeException("Patient already has insurance");
		}

		Insurance insurance = new Insurance();
		insurance.setPolicyNumber(request.getPolicyNumber());
		insurance.setProvider(request.getProvider());
		insurance.setValidUntil(request.getValidUntil());

		insurance.setPatient(patient);
		patient.setInsurance(insurance);

		return patientRepository.save(patient);
	}

	// ================= GET SINGLE =================
	public InsuranceResponseDto getInsurance(Long id) {

		Insurance insurance = insuranceRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Insurance not found"));

		return mapToDto(insurance);
	}

	// ================= DELETE =================
	@Transactional
	public void deleteInsurance(Long id) {

		Insurance insurance = insuranceRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Insurance not found"));

		Patient patient = insurance.getPatient();

		if (patient != null) {
			patient.setInsurance(null);
		}

		insuranceRepository.delete(insurance);
	}

	// ================= UPDATE =================
	@Transactional
	public InsuranceResponseDto updateInsurance(Long id, InsuranceRequestDto request) {

		Insurance insurance = insuranceRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Insurance not found"));

		if (request.getPolicyNumber() != null) {
			insurance.setPolicyNumber(request.getPolicyNumber());
		}

		if (request.getProvider() != null) {
			insurance.setProvider(request.getProvider());
		}

		if (request.getValidUntil() != null) {
			insurance.setValidUntil(request.getValidUntil());
		}

		if (request.getPatientId() != null) {

			Patient newPatient = patientRepository.findById(request.getPatientId())
					.orElseThrow(() -> new RuntimeException("Patient not found"));

			Patient oldPatient = insurance.getPatient();

			if (oldPatient != null) {
				oldPatient.setInsurance(null);
			}

			if (newPatient.getInsurance() != null) {
				throw new RuntimeException("New patient already has insurance");
			}

			insurance.setPatient(newPatient);
			newPatient.setInsurance(insurance);
		}

		insuranceRepository.save(insurance);

		return mapToDto(insurance);
	}

	// ================= GET ALL (SEARCH + FILTER + PAGINATION) =================
	public Page<InsuranceResponseDto> getAllInsurance(HttpServletRequest request) {

		int page = request.getParameter("page") != null
				? Integer.parseInt(request.getParameter("page"))
				: 0;

		int perPage = request.getParameter("perPage") != null
				? Integer.parseInt(request.getParameter("perPage"))
				: 5;

		String keyword = request.getParameter("keyword");
		String gender = request.getParameter("gender");

		Pageable pageable = PageRequest.of(page, perPage, Sort.by("id").descending());

		Specification<Insurance> spec = (root, query, cb) -> {

			var patientJoin = root.join("patient", JoinType.LEFT);
			Predicate predicate = cb.conjunction();

			if (keyword != null && !keyword.isBlank()) {

				String like = "%" + keyword.toLowerCase() + "%";

				Predicate search = cb.or(
						cb.like(cb.lower(patientJoin.get("name")), like),
						cb.like(cb.lower(patientJoin.get("email")), like),
						cb.like(cb.lower(root.get("policyNumber")), like),
						cb.like(cb.lower(root.get("provider")), like));
				if (keyword.matches("\\d+")) {
					search = cb.or(
							search,
							cb.equal(patientJoin.get("mobileNumber"), Long.parseLong(keyword)));
				}

				predicate = cb.and(predicate, search);
			}

			if (gender != null && !gender.isBlank()) {
				predicate = cb.and(predicate,
						cb.equal(cb.lower(patientJoin.get("gender")), gender.toLowerCase()));
			}

			return predicate;
		};

		Page<Insurance> insurancePage = insuranceRepository.findAll(spec, pageable);

		return insurancePage.map(this::mapToDto);
	}

	// ================= COMMON MAPPER =================
	private InsuranceResponseDto mapToDto(Insurance insurance) {

		Patient patient = insurance.getPatient();

		PatientResponseDto patientDto = null;

		if (patient != null) {

			String imageUrl = null;

			if (patient.getPatientImage() != null && !patient.getPatientImage().isBlank()) {
				imageUrl = helper.buildFileUrl(patient.getPatientImage());
			}

			patientDto = PatientResponseDto.builder()
					.id(patient.getId())
					.name(patient.getName())
					.email(patient.getEmail())
					.mobileNumber(patient.getMobileNumber())
					.gender(patient.getGender())
					.bloodGroup(patient.getBloodGroup() != null
							? patient.getBloodGroup().name()
							: null)
					.dateOfBirth(patient.getDateOfBirth())
					.createdDate(patient.getCreatedDate())
					.imageUrl(imageUrl)
					.build();
		}

		return InsuranceResponseDto.builder()
				.id(insurance.getId())
				.policyNumber(insurance.getPolicyNumber())
				.provider(insurance.getProvider())
				.validUntil(insurance.getValidUntil())
				.patient(patientDto)
				.build();
	}
}