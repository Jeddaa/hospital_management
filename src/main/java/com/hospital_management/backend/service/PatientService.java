package com.hospital_management.backend.service;

import com.hospital_management.backend.dto.request.CreatePatientDto;
import com.hospital_management.backend.dto.request.UpdatePatientDto;
import com.hospital_management.backend.dto.response.PatientResponseDto;
import com.hospital_management.backend.entity.PatientEntity;
import com.hospital_management.backend.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PatientService {

    //declaring the patientRepository that will be injected into this service
    private final PatientRepository patientRepository;

    //constructor
    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    //- Create a patient
    public PatientResponseDto createPatient(CreatePatientDto createPatientDto){
        PatientEntity getPatient = patientRepository.findByEmail(createPatientDto.getEmail());
        if(Objects.nonNull(getPatient)) {
            return PatientResponseDto.builder()
                    .firstName(getPatient.getFirstName())
                    .lastName(getPatient.getLastName())
                    .phoneNumber(getPatient.getPhoneNumber())
                    .email(getPatient.getEmail())
                    .dateOfBirth(getPatient.getDateOfBirth())
                    .address(getPatient.getAddress())
                    .build();
        }
        PatientEntity patientEntity = PatientEntity.builder()
                .firstName(createPatientDto.getFirstName())
                .lastName(createPatientDto.getLastName())
                .email(createPatientDto.getEmail())
                .phoneNumber(createPatientDto.getPhoneNumber())
                .dateOfBirth(createPatientDto.getDateOfBirth())
                .address(createPatientDto.getAddress())
                .build();
       PatientEntity createdPat = patientRepository.save(patientEntity);
       return PatientResponseDto.builder()
               .id(createdPat.getId())
               .firstName(createdPat.getFirstName())
               .lastName(createdPat.getLastName())
               .phoneNumber(createdPat.getPhoneNumber())
               .email(createdPat.getEmail())
               .dateOfBirth(createdPat.getDateOfBirth())
               .address(createdPat.getAddress())
               .build();
    }
    //- View all patients

    public List<PatientResponseDto> getAllPatients(){
        return patientRepository.findAll()
                .stream()
                .map(patient -> PatientResponseDto.builder()
                        .id(patient.getId())
                        .firstName(patient.getFirstName())
                        .lastName(patient.getLastName())
                        .dateOfBirth(patient.getDateOfBirth())
                        .email(patient.getEmail())
                        .phoneNumber(patient.getPhoneNumber())
                        .address(patient.getAddress())
                        .build())
                .toList();
    }
    //- View a patient by ID
    public PatientResponseDto getOnePatientById(Long id){
        Optional<PatientEntity> findOne = patientRepository.findById(id);
        if(findOne.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "patient not found");
        PatientEntity patientEntity = findOne.get();
        return PatientResponseDto.builder()
                .id(patientEntity.getId())
                .firstName(patientEntity.getFirstName())
                .lastName(patientEntity.getLastName())
                .dateOfBirth(patientEntity.getDateOfBirth())
                .email(patientEntity.getEmail())
                .phoneNumber(patientEntity.getPhoneNumber())
                .address(patientEntity.getAddress())
                .build();
    }
    //- Update a patient
    public PatientEntity updatePatient(Long id, UpdatePatientDto updatedPatient){
        Optional<PatientEntity> findOne = patientRepository.findById(id);
        if(findOne.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "patient not found");
        PatientEntity foundPatient = findOne.get();
        //should only be able to update phone number, address, email and date of birth
        //@ask legends if any other check is required
        if(Objects.nonNull(updatedPatient.getPhoneNumber())) foundPatient.setPhoneNumber(updatedPatient.getPhoneNumber());
        if(Objects.nonNull(updatedPatient.getEmail())) foundPatient.setEmail(updatedPatient.getEmail());
        if(Objects.nonNull(updatedPatient.getAddress())) foundPatient.setAddress(updatedPatient.getAddress());
        if(Objects.nonNull(updatedPatient.getDateOfBirth())) foundPatient.setDateOfBirth(updatedPatient.getDateOfBirth());
        return patientRepository.save(foundPatient);
    }

    //delete a patient
    public String deletePatient(Long id){
        Optional<PatientEntity> findOne = patientRepository.findById(id);
        if(findOne.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "patient not found");
        PatientEntity foundPatient = findOne.get();
        patientRepository.delete(foundPatient);
        return "Patient deleted successfully";
    }
}
