package com.springboot.project.hospitalManagement.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Insurance {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	
	@Column(nullable = false, unique=true, length=50)
	private String policyNumber;
	
	@Column(nullable = false, length=100)
	private String provider;
	
	@Column(nullable = false, length=100)
	private LocalDate validUntil;
	
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	// for bidirectional mapping (inverse side, owning side is Patient)
	@OneToOne(mappedBy = "insurance")
	private Patient patient;
}
