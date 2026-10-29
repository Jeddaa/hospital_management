package com.hospital_management.backend.entity;

import com.hospital_management.backend.types.DoctorStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String specialization;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private DoctorStatus status = DoctorStatus.DEFAULT;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<AppointmentEntity> appointments = new ArrayList<>();
}
