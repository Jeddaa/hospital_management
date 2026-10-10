package com.hospital_management.backend.dto.request;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PrescriptionRequestDto{
    private Long appointmentId;
    private String medicationName;
    private String dosage;
    private String instructions;
}
