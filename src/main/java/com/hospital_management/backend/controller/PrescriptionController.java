package com.hospital_management.backend.controller;

import com.hospital_management.backend.dto.request.PrescriptionRequestDto;
import com.hospital_management.backend.dto.response.PrescriptionResponseDto;
import com.hospital_management.backend.entity.PrescriptionEntity;
import com.hospital_management.backend.service.PrescriptionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prescription")
public class PrescriptionController {
    private final PrescriptionService prescriptionService;

    PrescriptionController (PrescriptionService prescriptionService){
        this.prescriptionService = prescriptionService;
    }

    @PostMapping
    PrescriptionResponseDto createPrescription(@RequestBody PrescriptionRequestDto createData){
        return prescriptionService.createPrescription(createData);
    }

    @GetMapping("appointment/{id}")
    List<PrescriptionEntity> getPrescriptionsForAppointment(@PathVariable() Long id){
        return prescriptionService.getPrescriptions(id);
    }

    @GetMapping("/{id}")
    PrescriptionResponseDto getOnePrescription(@PathVariable() Long id){
        return prescriptionService.getPrescription(id);
    }




}
