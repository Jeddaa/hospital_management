package com.hospital_management.backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UpdatePatientDto {

    @Email(message = "Email should be valid")
    private String email;

    @Size(max = 15, message = "Phone number cannot exceed 100 characters")
    private String phoneNumber;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    private String address;
}
