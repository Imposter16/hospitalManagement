package com.springboot.project.hospitalManagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import jakarta.validation.Validator;
import com.springboot.project.hospitalManagement.dto.PatientRequestDto;

@SpringBootTest
public class ValidationTest {

    @Autowired
    private Validator validator;

    @Test
    public void testValidation() {
        PatientRequestDto dto = new PatientRequestDto();
        dto.setName("");
        dto.setEmail("pamnb1");
        dto.setMobileNumber(1281L);
        dto.setGender("Femalez");
        
        System.out.println("VALIDATOR: " + validator.getClass().getName());
        var violations = validator.validate(dto);
        System.out.println("VIOLATIONS COUNT: " + violations.size());
        violations.forEach(v -> System.out.println("VIOLATION: " + v.getPropertyPath() + " " + v.getMessage()));
    }
}
