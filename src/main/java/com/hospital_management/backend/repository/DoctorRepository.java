package com.hospital_management.backend.repository;

import com.hospital_management.backend.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository <DoctorEntity, Long> {
}
