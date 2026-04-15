package com.springboot.project.hospitalManagement.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "patient", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "email", "mobile_number" })
}, indexes = {
        @Index(name = "idx_patient_name", columnList = "name")
})
@Getter
@Setter
@ToString
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private String email;

    @Column(name = "mobile_number")
    private Long mobileNumber;

    @ToString.Exclude
    private String gender;

    @Column(name = "blood_group")
    private BloodGroup bloodGroup;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
}