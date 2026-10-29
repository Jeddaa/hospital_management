package com.hospital_management.backend.service;

import com.hospital_management.backend.dto.request.PrescriptionRequestDto;
import com.hospital_management.backend.dto.response.PrescriptionResponseDto;
import com.hospital_management.backend.entity.AppointmentEntity;
import com.hospital_management.backend.entity.PatientEntity;
import com.hospital_management.backend.entity.PrescriptionEntity;
import com.hospital_management.backend.repository.AppointmentRepository;
import com.hospital_management.backend.repository.PrescriptionRepository;
import com.hospital_management.backend.types.AppointmentStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class PrescriptionService{
    private final AppointmentRepository appointmentRepository;
    private final PrescriptionRepository prescriptionRepository;
    PrescriptionService(PrescriptionRepository prescriptionRepository, AppointmentRepository appointmentRepository){
        this.prescriptionRepository = prescriptionRepository;
        this.appointmentRepository = appointmentRepository;
    }

    //- Create a prescription
    public PrescriptionResponseDto createPrescription(PrescriptionRequestDto createData){
        // get appointment and confirm status
        AppointmentEntity oneAppointment = appointmentRepository.findById(createData.getAppointmentId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found"));
        if(oneAppointment.getStatus() != AppointmentStatus.COMPLETED) throw new ResponseStatusException(HttpStatus.TOO_EARLY, "Appointment has to be completed before prescription");
        PrescriptionEntity createPrescription = PrescriptionEntity.builder()
                .appointment(oneAppointment)
                .medicationName(createData.getMedicationName())
                .dosage(createData.getDosage())
                .instructions(createData.getInstructions())
                .dosage(createData.getDosage())
                .build();
        PrescriptionEntity pres = prescriptionRepository.save(createPrescription);
        return PrescriptionResponseDto.builder()
                .id(pres.getId())
                .appointmentId(oneAppointment.getId())
                .dosage(pres.getDosage())
                .medicationName(pres.getMedicationName())
                .instructions(pres.getInstructions())
                .build();
    }

    //- View a prescription by ID
    public PrescriptionResponseDto getPrescription(Long id){
       PrescriptionEntity getPrescription = prescriptionRepository.findById(id).orElseThrow(() ->
               new ResponseStatusException(HttpStatus.NOT_FOUND, "Prescription not found"));

        return PrescriptionResponseDto.builder()
                .appointmentId(getPrescription.getAppointment().getId())
                .dosage(getPrescription.getDosage())
                .medicationName(getPrescription.getMedicationName())
                .instructions(getPrescription.getInstructions())
                .id(getPrescription.getId())
                .build();
    }
    //- View prescriptions belonging to an appointment
    public List<PrescriptionResponseDto> getPrescriptions(Long id){
       AppointmentEntity getApt = appointmentRepository.findById(id).orElseThrow(()->
               new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found"));

        return prescriptionRepository.findByAppointmentId(getApt.getId())
                .stream()
                .map(prescription -> PrescriptionResponseDto.builder()
                        .id(prescription.getId())
                        .dosage(prescription.getDosage())
                        .appointmentId(prescription.getAppointment().getId())
                        .medicationName(prescription.getMedicationName())
                        .instructions(prescription.getInstructions())
                        .build())
                .toList();
    }

    public String deletePrescription(Long id){
        PrescriptionEntity findOne = prescriptionRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "patient not found"));
        prescriptionRepository.delete(findOne);
        return "Prescription deleted successfully";
    }
}
