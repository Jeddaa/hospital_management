package com.hospital_management.backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PrescriptionResponseDto {
    private Long id;
    private Long appointmentId;
    private String medicationName;
    private String dosage;
    private String instructions;
}
