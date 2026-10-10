package com.hospital_management.backend.service;

import com.hospital_management.backend.dto.request.PrescriptionRequestDto;
import com.hospital_management.backend.dto.response.PrescriptionResponseDto;
import com.hospital_management.backend.entity.AppointmentEntity;
import com.hospital_management.backend.entity.PrescriptionEntity;
import com.hospital_management.backend.repository.AppointmentRepository;
import com.hospital_management.backend.repository.PrescriptionRepository;
import com.hospital_management.backend.types.AppointmentStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
        Optional<AppointmentEntity> getApt = appointmentRepository.findById(createData.getAppointmentId());
        if(getApt.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found");
        if(getApt.get().getStatus() != AppointmentStatus.COMPLETED) throw new ResponseStatusException(HttpStatus.TOO_EARLY, "Appointment has to be completed before prescription");
        PrescriptionEntity createPrescription = PrescriptionEntity.builder()
                .appointmentId(createData.getAppointmentId())
                .medicationName(createData.getMedicationName())
                .dosage(createData.getDosage())
                .instructions(createData.getInstructions())
                .dosage(createData.getDosage())
                .build();
        PrescriptionEntity pres = prescriptionRepository.save(createPrescription);
        return PrescriptionResponseDto.builder()
                .appointmentId(createData.getAppointmentId())
                .dosage(createData.getDosage())
                .medicationName(createData.getMedicationName())
                .instructions(createData.getInstructions())
                .build();
    }

    //- View a prescription by ID
    public PrescriptionResponseDto getPrescription(Long id){
        Optional<PrescriptionEntity> getPres = prescriptionRepository.findById(id);
        if(getPres.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Prescription not found");
        PrescriptionEntity foundPres = getPres.get();
        return PrescriptionResponseDto.builder()
                .appointmentId(foundPres.getAppointmentId())
                .dosage(foundPres.getDosage())
                .medicationName(foundPres.getMedicationName())
                .instructions(foundPres.getInstructions())
                .Id(foundPres.getId())
                .build();
    }
    //- View prescriptions belonging to an appointment
    public List<PrescriptionEntity> getPrescriptions(Long id){
        Optional<AppointmentEntity> getApt = appointmentRepository.findById(id);
        if(getApt.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found");
        AppointmentEntity foundApt = getApt.get();

        return prescriptionRepository.findByAppointmentId(id);
    }
}
