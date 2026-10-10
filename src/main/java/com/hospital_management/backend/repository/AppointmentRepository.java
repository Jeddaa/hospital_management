package com.hospital_management.backend.repository;

import com.hospital_management.backend.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository <AppointmentEntity, Long> {
    AppointmentEntity findByDoctorId(Long doctorId);
    AppointmentEntity findByPatientId(Long patientId);

//    boolean existsByDoctorIdAndAppointmentDateAndAppointmentTime(
//            Long doctorId, LocalDate appointmentDate, LocalTime appointmentTime);
}
