package com.hospital_management.backend.dto.response;

import com.hospital_management.backend.types.AppointmentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Builder
@Getter
public class AppointmentResponseDto {
    private long Id;
    private String patientName;
    private String doctorName;
    private String doctorSpecialization;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private AppointmentStatus appointmentStatus;
}
