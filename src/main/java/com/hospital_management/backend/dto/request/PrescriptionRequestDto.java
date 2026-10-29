package com.hospital_management.backend.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PrescriptionRequestDto{
    @NotBlank(message="Appointment Id is required")
    private Long appointmentId;

    @NotBlank(message="Medication name is required")
    private String medicationName;

    @NotBlank(message="Dosage is required")
    private String dosage;

    @NotBlank(message="Instructions are required")
    private String instructions;
}
