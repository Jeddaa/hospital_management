package com.hospital_management.backend.repository;

import com.hospital_management.backend.entity.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity, Long> {

    List<PrescriptionEntity> findByAppointmentId(Long appointmentId);
}
