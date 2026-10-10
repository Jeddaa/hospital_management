package com.hospital_management.backend.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class DoctorResponseDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String specialization;

}
