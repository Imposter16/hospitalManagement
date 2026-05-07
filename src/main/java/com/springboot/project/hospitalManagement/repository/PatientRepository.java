package com.springboot.project.hospitalManagement.repository;

import java.util.*;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.project.hospitalManagement.dto.BloodGroupCountEntity;
import com.springboot.project.hospitalManagement.entity.Patient;

import jakarta.transaction.Transactional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

	Patient findByName(String name);

	List<Patient> findByDateOfBirthOrEmail(LocalDate dateOfBirth, String email);

	@Query("SELECT p FROM Patient p WHERE p.bloodGroup=?1")
	List<Patient> findByBloodGroup(@Param("bloodGroup") String bloodGroup);// write the code in test class also

	@Query("SELECT p FROM Patient p where p.dateOfBirth > :dateOfBirth")
	List<Patient> findByBornAfter(@Param("dateOfBirth") LocalDate dateOfBirth);

	@Query(value = "SELECT * FROM patient", nativeQuery = true)
	// List<Patient> findAllPatients();
	Page<Patient> findAllPatients(Pageable pagable);

	@Modifying
	@Transactional
	@Query("UPDATE Patient p SET p.name =:name WHERE p.id=:id")
	int updateNameWithId(@Param("name") String name, @Param("id") Long id);

	// You must use the FULL package name for the DTO inside the query
	@Query("SELECT new com.springboot.project.hospitalManagement.dto.BloodGroupCountEntity(p.bloodGroup, COUNT(p)) " +
			"FROM Patient p GROUP BY p.bloodGroup")
	List<BloodGroupCountEntity> countPatientsByBloodGroup();

	boolean existsByEmail(String email);

	boolean existsByMobileNumber(Long mobileNumber);

}
