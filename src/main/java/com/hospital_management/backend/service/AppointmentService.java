package com.hospital_management.backend.service;

import com.hospital_management.backend.dto.request.AppointmentRequestDto;
import com.hospital_management.backend.dto.response.AppointmentResponseDto;
import com.hospital_management.backend.dto.response.DoctorResponseDto;
import com.hospital_management.backend.entity.AppointmentEntity;
import com.hospital_management.backend.entity.DoctorEntity;
import com.hospital_management.backend.entity.PatientEntity;
import com.hospital_management.backend.entity.PrescriptionEntity;
import com.hospital_management.backend.repository.AppointmentRepository;
import com.hospital_management.backend.repository.DoctorRepository;
import com.hospital_management.backend.repository.PatientRepository;
import com.hospital_management.backend.types.AppointmentStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    AppointmentService(AppointmentRepository appointmentRepository,
                       PatientRepository patientRepository, DoctorRepository doctorRepository){
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    //- Create an appointment
    public AppointmentResponseDto createAppointment(AppointmentRequestDto requestDto)  {

        //get the doctor details and throw error if not found
        DoctorEntity foundDoctor = doctorRepository.findById(requestDto.getDoctorId()).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor does not exist"));

        // get the patient details and throw error if not found
        PatientEntity foundPatient = patientRepository.findById(requestDto.getPatientId()).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient does not exist"));

        // check if the doctor has an appointment at the proposed date and time

        boolean doctorBooked = appointmentRepository.existsByDoctorIdAndAppointmentDateAndAppointmentTime(
                requestDto.getDoctorId(), requestDto.getAppointmentDate(), requestDto.getAppointmentTime());

        // check if the patient has an appointment at the proposed date and time
        boolean patientBooked = appointmentRepository.existsByPatientIdAndAppointmentDateAndAppointmentTime(
                requestDto.getPatientId(), requestDto.getAppointmentDate(), requestDto.getAppointmentTime());

        if(doctorBooked) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Doctor already has an appointment at this date and time");

        if(patientBooked) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient already has an appointment at this date and time");

        // check if appointment date and time is before today and now
        if(requestDto.getAppointmentDate().isBefore(LocalDate.now()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment cannot be booked for past dates and time");

        AppointmentEntity createAppointment = AppointmentEntity.builder()
                .patient(foundPatient)
                .doctor(foundDoctor)
                .appointmentDate(requestDto.getAppointmentDate())
                .appointmentTime(requestDto.getAppointmentTime())
                .reason(requestDto.getReason())
                .build();
        AppointmentEntity createdApt = appointmentRepository.save(createAppointment);

        return getResponseDto(createdApt, foundPatient, foundDoctor);
    }



    //- View all appointments
    public List<AppointmentResponseDto> getAllAppointments(){
        return appointmentRepository.findAll()
                .stream()
                .map(appt-> AppointmentResponseDto.builder()
                        .id(appt.getId())
                        .patientName(appt.getPatient().getFirstName() + " " + appt.getPatient().getLastName())
                        .doctorName(appt.getDoctor().getFirstName() + " " + appt.getDoctor().getLastName())
                        .appointmentStatus(appt.getStatus())
                        .doctorSpecialization(appt.getDoctor().getSpecialization())
                        .appointmentDate(appt.getAppointmentDate())
                        .appointmentTime(appt.getAppointmentTime())
                        .build())
                .toList();
    }

    //- View an appointment by ID
    public AppointmentResponseDto getOneAppointment(Long id){
        // get appointment
        AppointmentEntity oneAppointment = appointmentRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment Not found"));

        return getResponseDto(oneAppointment, oneAppointment.getPatient(), oneAppointment.getDoctor());
    }

    //- Cancel an appointment
    public AppointmentResponseDto cancelAppointment(Long id){
        // get appointment
        AppointmentEntity oneAppointment = appointmentRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment Not found"));

        if(oneAppointment.getStatus().equals(AppointmentStatus.COMPLETED)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment already completed.");
        oneAppointment.setStatus(AppointmentStatus.CANCELLED);
        AppointmentEntity cancelled =  appointmentRepository.save(oneAppointment);
        return getResponseDto(cancelled, cancelled.getPatient(), cancelled.getDoctor());
    }

    //- Complete an appointment
    public AppointmentResponseDto completeAppointment(Long id){
        // get appointment
        AppointmentEntity oneAppointment = appointmentRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment Not found"));
        if(oneAppointment.getStatus().equals(AppointmentStatus.CANCELLED)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment already cancelled.");
        oneAppointment.setStatus(AppointmentStatus.COMPLETED);
        AppointmentEntity saved = appointmentRepository.save(oneAppointment);
        return getResponseDto(saved, saved.getPatient(), saved.getDoctor());
    }

    private static AppointmentResponseDto getResponseDto(AppointmentEntity createdApt, PatientEntity foundPatient, DoctorEntity foundDoctor) {
        return AppointmentResponseDto.builder()
                .id(createdApt.getId())
                .patientName(foundPatient.getFirstName() + " " + foundPatient.getLastName())
                .doctorName(foundDoctor.getFirstName() + " " + foundDoctor.getLastName())
                .doctorSpecialization(foundDoctor.getSpecialization())
                .appointmentTime(createdApt.getAppointmentTime())
                .appointmentDate(createdApt.getAppointmentDate())
                .appointmentStatus(createdApt.getStatus())
                .build();
    }

    public String deletePrescription(Long id){
        AppointmentEntity findOne = appointmentRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "patient not found"));
        appointmentRepository.delete(findOne);
        return "Appointment deleted successfully";
    }
}
