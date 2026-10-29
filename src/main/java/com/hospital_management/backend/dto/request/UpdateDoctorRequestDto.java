package com.hospital_management.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateDoctorRequestDto {

    @Size(max = 15, message = "Phone number cannot exceed 100 characters")
    private String phoneNumber;

    @Email(message = "Email should be valid")
    private String email;

    private String specialization;
}
