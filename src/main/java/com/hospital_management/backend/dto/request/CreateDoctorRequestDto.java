package com.hospital_management.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateDoctorRequestDto {

    @NotBlank(message="First name is compulsory")
    @Size(max = 100, message = "First Name cannot exceed 100 characters")
    private String firstName;

    @NotBlank(message="Last name is compulsory")
    @Size(max = 100, message = "Last Name cannot exceed 100 characters")
    private String lastName;

    @NotBlank(message="Phone Number is compulsory")
    private String phoneNumber;

    @NotBlank(message="Email is compulsory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message="Specialization is compulsory")
    private String specialization;
}
