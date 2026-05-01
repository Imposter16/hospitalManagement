package com.springboot.project.hospitalManagement.entity;

import org.hibernate.annotations.AnyDiscriminatorImplicitValues.Strategy;
import java.util.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false, unique = true, length = 265)
	private String name;
	
	@OneToOne
	private Doctor headDoctor;
	
	@ManyToMany
	private Set<Doctor> doctors=new HashSet<>();
	
}
