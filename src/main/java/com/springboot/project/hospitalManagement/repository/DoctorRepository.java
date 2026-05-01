package com.springboot.project.hospitalManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.project.hospitalManagement.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{

}
