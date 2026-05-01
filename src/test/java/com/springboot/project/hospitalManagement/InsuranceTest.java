package com.springboot.project.hospitalManagement;

import java.awt.desktop.UserSessionEvent.Reason;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.project.hospitalManagement.entity.Appointment;
import com.springboot.project.hospitalManagement.entity.Insurance;
import com.springboot.project.hospitalManagement.entity.Patient;
import com.springboot.project.hospitalManagement.service.AppointmentService;
import com.springboot.project.hospitalManagement.service.InsuranceService;

@SpringBootTest
public class InsuranceTest {
	
	@Autowired
	private InsuranceService insuranceService;
	
	
	
	
//	@Test
//	public void setInsurance() {
////		Insurance insureance=new Insurance();     Old method
//		
//		// Using Lombok Builder method
//		
//		Insurance insurance=Insurance.builder().
//				policyNumber("HDFC-INS15025").provider("HDFC").validUntil(LocalDate.of(2030, 12, 6)).build();
//		
//		Patient patient=insuranceService.assignInsuranceToPatient(insurance, 82L);
//		System.out.println(patient);
//		
//	}
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Test
	public void testCreateAppointment() {
		Appointment appointment=Appointment.builder().
				appoinmentTime(LocalDateTime.of(2026,5,11, 20,50)).
				reason("Headache").
				build();
		
		var newAppointment=appointmentService.createAppointment(appointment, 1L, 88L);
		
		System.out.println(newAppointment);
		
		var updatedAppointment=appointmentService.reassignAppointment(newAppointment.getId(), 3L);
		
		System.out.println(updatedAppointment);
	}
}
