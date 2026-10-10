package com.hospital_management.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class CreatePatientDto {

    @NotBlank(message = "First Name required")
    @Size(max = 100, message = "First Name cannot exceed 100 characters")
    private String firstName;

    @NotBlank(message = "First Name required")
    @Size(max = 100, message = "First Name cannot exceed 100 characters")
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String address;
}
