package com.springboot.project.hospitalManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.project.hospitalManagement.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
