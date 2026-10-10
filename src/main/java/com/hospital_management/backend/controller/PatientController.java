package com.hospital_management.backend.controller;

import com.hospital_management.backend.dto.request.CreatePatientDto;
import com.hospital_management.backend.dto.response.PatientResponseDto;
import com.hospital_management.backend.entity.PatientEntity;
import com.hospital_management.backend.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    PatientController(PatientService patientService){
        this.patientService = patientService;
    }

    @PostMapping
    public PatientResponseDto createPatient(@Valid @RequestBody CreatePatientDto createData){
        return patientService.createPatient(createData);
    }

    @GetMapping()
    public List<PatientEntity> getAllPatient(){
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public PatientResponseDto getOnePatient(@PathVariable() Long id){
        return patientService.getOnePatientById(id);
    }

    @PatchMapping("/{id}")
    public PatientEntity updatePatient(@PathVariable Long id, @Valid @RequestBody CreatePatientDto createData){
        return patientService.updatePatient(id, createData);
    }

    @DeleteMapping("/{id}")
    public String deletePatient(@PathVariable Long id){
        return patientService.deletePatient(id);
    }

}
