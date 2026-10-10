package com.hospital_management.backend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateDoctorRequestDto {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String specialization;
}
