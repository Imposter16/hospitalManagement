package com.springboot.project.hospitalManagement.service;

import org.springframework.stereotype.Service;

import com.springboot.project.hospitalManagement.entity.Appointment;
import com.springboot.project.hospitalManagement.entity.Doctor;
import com.springboot.project.hospitalManagement.entity.Patient;
import com.springboot.project.hospitalManagement.repository.AppointmentRepository;
import com.springboot.project.hospitalManagement.repository.DoctorRepository;
import com.springboot.project.hospitalManagement.repository.PatientRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService {
	private final DoctorRepository doctorRepository;
	private final PatientRepository patientRepository;
	private final AppointmentRepository appointmentRepository;
	
	@Transactional
	public Appointment createAppointment(Appointment appointment, Long doctorId, Long patientId) {
		Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
		Patient patient = patientRepository.findById(patientId).orElseThrow();
		
		if(appointment.getId() !=null) throw new IllegalArgumentException();
		
		appointment.setPatient(patient);
		appointment.setDoctor(doctor);
		
		patient.getAppointments().add(appointment);
		
		return appointmentRepository.save(appointment);
		
		
	}
	
	@Transactional
	public Appointment reassignAppointment(Long appointmentId, Long doctorId) {
		Appointment appointment=appointmentRepository.findById(appointmentId).orElseThrow();
		Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
		
		appointment.setDoctor(doctor);
		
		return appointment;
	}
	
}
