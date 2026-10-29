package com.hospital_management.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AppointmentRequestDto {

    @NotNull(message="Patient Id is required")
    private Long patientId;

    @NotNull(message="Doctor Id is required")
    private Long doctorId;

    @NotNull(message="appointment date is required")
    @FutureOrPresent(message = "Appointment date must be present or in the future")
    private LocalDate appointmentDate;

    @NotNull(message="appointment time is required")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime appointmentTime;

    private String reason;
}
