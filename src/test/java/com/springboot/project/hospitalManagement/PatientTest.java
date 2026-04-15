package com.springboot.project.hospitalManagement;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.springboot.project.hospitalManagement.dto.BloodGroupCountEntity;
import com.springboot.project.hospitalManagement.entity.Patient;
import com.springboot.project.hospitalManagement.repository.PatientRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class PatientTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testRepositoryQueries() throws InterruptedException {
        // System.out.println("--- STARTING QUERY TESTS ---");

        // System.out.println("\n>> Testing findByName('Priya Verma'):");
        // Patient p = patientRepository.findByName("Priya Verma");
        // if (p != null) {
        // System.out.println("Found: " + p.getName() + " with Email: " + p.getEmail());
        // }

        // System.out.println("\n>> Testing findByDateOfBirthOrEmail:");
        // List<Patient> byDateOrEmail = patientRepository.findByDateOfBirthOrEmail(
        // LocalDate.of(2002, 11, 8), "test@random.com");
        // byDateOrEmail.forEach(patient -> System.out.println("Result: " +
        // patient.getName()));

        // System.out.println("\n>> Testing findByBloodGroup('B+'):");
        // List<Patient> byBlood = patientRepository.findByBloodGroup("B+");
        // byBlood.forEach(patient -> System.out.println("Blood Group B+ Patient: " +
        // patient.getName()));

        // System.out.println("\n>> Testing findByBornAfter (Post-1998):");
        // List<Patient> youngPatients =
        // patientRepository.findByBornAfter(LocalDate.of(1998, 1, 1));
        // youngPatients.forEach(patient -> System.out
        // .println("Born After 1998: " + patient.getName() + " [" +
        // patient.getDateOfBirth() + "]"));

        // System.out.println("\n--- ALL TESTS COMPLETED ---");
        // System.out.flush();
        // Thread.sleep(1000);

        // Writing native queries

//         List<Patient> patientList = patientRepository.findAllPatients();
//         patientList.forEach(patient -> System.out.println(patient));

        // Create Query

        int updateName = patientRepository.updateNameWithId("Amit WWWW", 83L);
        System.out.println(updateName);
        
        
        System.out.println("--- Testing Blood Group Projection ---");
        List<BloodGroupCountEntity> counts = patientRepository.countPatientsByBloodGroup();
        
        counts.forEach(item -> {
            System.out.println("Blood Group: " + item.getBloodGroupType() + 
                               " | Count: " + item.getCount());
        });
        
        
        // Pagination
        
        Page<Patient> patientList = patientRepository.findAllPatients(PageRequest.of(0, 2));
        patientList.forEach(patient -> System.out.println(patient));

    }
}