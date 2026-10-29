package com.hospital_management.backend.service;

import com.hospital_management.backend.dto.request.CreateDoctorRequestDto;
import com.hospital_management.backend.dto.request.UpdateDoctorRequestDto;
import com.hospital_management.backend.dto.request.UpdatePatientDto;
import com.hospital_management.backend.dto.response.DoctorResponseDto;
import com.hospital_management.backend.entity.DoctorEntity;
import com.hospital_management.backend.repository.DoctorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    DoctorService(DoctorRepository doctorRepository){
        this.doctorRepository = doctorRepository;
    }

    //- Create a doctor
    public DoctorResponseDto createDoctor(CreateDoctorRequestDto createData){
        DoctorEntity createDoctor = DoctorEntity.builder()
                .firstName(createData.getFirstName())
                .lastName(createData.getLastName())
                .email(createData.getEmail())
                .phoneNumber(createData.getPhoneNumber())
                .specialization(createData.getSpecialization())
                .build();

        DoctorEntity createdDoc = doctorRepository.save(createDoctor);
        return DoctorResponseDto.builder()
                .id(createdDoc.getId())
                .firstName(createdDoc.getFirstName())
                .lastName(createdDoc.getLastName())
                .email(createdDoc.getEmail())
                .phoneNumber(createdDoc.getPhoneNumber())
                .specialization(createdDoc.getSpecialization())
                .build();
    }

    //- View all doctors
    public List<DoctorResponseDto> getAllDoctor(){
        return doctorRepository.findAll()
                .stream()
                .map(doctor-> DoctorResponseDto.builder()
                        .id(doctor.getId())
                        .firstName(doctor.getFirstName())
                        .lastName(doctor.getLastName())
                        .email(doctor.getEmail())
                        .phoneNumber(doctor.getPhoneNumber())
                        .specialization(doctor.getSpecialization())
                        .build())
                .toList();
    }

    //- View a doctor by ID
    public DoctorResponseDto getOneDoctor(Long id){
        Optional<DoctorEntity> findDoctor = doctorRepository.findById(id);
        if(findDoctor.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found");
        DoctorEntity foundDoctor = findDoctor.get();
        return DoctorResponseDto.builder()
                .id(foundDoctor.getId())
                .firstName(foundDoctor.getFirstName())
                .lastName(foundDoctor.getLastName())
                .email(foundDoctor.getEmail())
                .phoneNumber(foundDoctor.getPhoneNumber())
                .specialization(foundDoctor.getSpecialization())
                .build();
    }

    //- Update a doctor
    public DoctorResponseDto updateOneDoctor(Long id, UpdateDoctorRequestDto updateDoctor){
        Optional<DoctorEntity> findDoctor = doctorRepository.findById(id);
        if(findDoctor.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found");
        DoctorEntity foundDoctor = findDoctor.get();

        if(Objects.nonNull(updateDoctor.getEmail())) foundDoctor.setEmail(updateDoctor.getEmail());
        if(Objects.nonNull(updateDoctor.getPhoneNumber())) foundDoctor.setPhoneNumber(updateDoctor.getPhoneNumber());
        if(Objects.nonNull(updateDoctor.getSpecialization())) foundDoctor.setSpecialization(updateDoctor.getSpecialization());

        DoctorEntity foundDoc = doctorRepository.save(foundDoctor);
        return DoctorResponseDto.builder()
                .id(foundDoc.getId())
                .firstName(foundDoc.getFirstName())
                .lastName(foundDoc.getLastName())
                .email(foundDoc.getEmail())
                .phoneNumber(foundDoc.getPhoneNumber())
                .specialization(foundDoc.getSpecialization())
                .build();
    }

    //- Delete a doctor
    public String deleteOneDoctor(Long id){
        Optional<DoctorEntity> findDoctor = doctorRepository.findById(id);
        if(findDoctor.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found");
        doctorRepository.deleteById(findDoctor.get().getId());
        return "Doctor deleted sucessfully";
    }
}
