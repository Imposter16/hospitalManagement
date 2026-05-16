package com.springboot.project.hospitalManagement.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class PatientRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Mobile number is required")
    @Min(value = 1000000L, message = "Mobile number must be at least 7 digits")
    @Max(value = 999999999999999L, message = "Mobile number must not exceed 15 digits")
    private Long mobileNumber;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^(Male|Female|Trans)$", message = "Gender must be Male, Female or Trans")
    private String gender;

    @NotBlank(message = "Blood group is required")
    @Pattern(regexp = "^(A_POS|A_NEG|B_POS|B_NEG|O_POS|O_NEG|AB_POS|AB_NEG)$", message = "Invalid blood group")
    private String bloodGroup;

    @Past(message = "Date of birth must be in the past")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;

    private MultipartFile image;
}
