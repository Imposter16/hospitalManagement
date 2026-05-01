package com.springboot.project.hospitalManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.project.hospitalManagement.entity.Insurance;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {

}
