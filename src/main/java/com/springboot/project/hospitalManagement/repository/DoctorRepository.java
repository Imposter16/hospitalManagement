package com.springboot.project.hospitalManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.project.hospitalManagement.entity.Doctor;
import java.util.*;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsByEmail(String email);

    Optional<Doctor> findByEmail(String email);
}
