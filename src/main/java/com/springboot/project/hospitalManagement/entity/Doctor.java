package com.springboot.project.hospitalManagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length=265)
	private String name;
	
	@Column(length = 100)
	private String Specilization;
	
	@Column(nullable = false, unique = true, length = 265)
	private String email;
	
	@ManyToMany(mappedBy = "doctors")
	private Set<Department> departments=new HashSet<>();
	
}
