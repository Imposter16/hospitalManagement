package com.springboot.project.hospitalManagement.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class PatientResponseDto {

	private Long id;
	private String name;
	private String email;
	private Long mobileNumber;
	private String gender;
	private String bloodGroup;
	private LocalDate dateOfBirth;
	private LocalDateTime createdDate;

	private String imageUrl;

}
