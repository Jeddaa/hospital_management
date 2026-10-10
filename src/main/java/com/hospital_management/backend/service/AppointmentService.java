package com.hospital_management.backend.service;

import com.hospital_management.backend.dto.request.AppointmentRequestDto;
import com.hospital_management.backend.dto.response.AppointmentResponseDto;
import com.hospital_management.backend.entity.AppointmentEntity;
import com.hospital_management.backend.entity.DoctorEntity;
import com.hospital_management.backend.entity.PatientEntity;
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
import java.util.Objects;
import java.util.Optional;

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

        //get the doctor details
        Optional<DoctorEntity> getDoctor = doctorRepository.findById(requestDto.getDoctorId());
        if(getDoctor.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found");
        DoctorEntity doctorFound = getDoctor.get();

        //get the patient details
        Optional<PatientEntity> getPatient = patientRepository.findById(requestDto.getPatientId());
        if(getPatient.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");
        PatientEntity patientFound = getPatient.get();

        LocalDate today = LocalDate.now();
        LocalTime time = LocalTime.now();

        if(requestDto.getAppointmentDate().isBefore(today)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment cannot be booked for past dates");

        AppointmentEntity appointment = appointmentRepository.findByDoctorId(requestDto.getDoctorId());

        if(Objects.nonNull(appointment.getAppointmentDate())) {
            if (appointment.getAppointmentDate().isEqual(requestDto.getAppointmentDate()) && !appointment.getPatientId().equals(requestDto.getPatientId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Doctor has another appointment at this time");
            }
        }

        AppointmentEntity getPatientApt = appointmentRepository.findByPatientId(requestDto.getPatientId());
        if(getPatientApt.getAppointmentDate().isEqual(requestDto.getAppointmentDate()) && getPatientApt.getAppointmentTime().equals(requestDto.getAppointmentTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient has an appointment at this time");
        }
        AppointmentEntity createAppointment = AppointmentEntity.builder()
                .patientId(requestDto.getPatientId())
                .doctorId(requestDto.getDoctorId())
                .appointmentDate(requestDto.getAppointmentDate())
                .appointmentTime(requestDto.getAppointmentTime())
                .reason(requestDto.getReason())
                .build();
        AppointmentEntity createdApt = appointmentRepository.save(createAppointment);

        return AppointmentResponseDto.builder()
                .Id(createdApt.getId())
                .patientName(patientFound.getFirstName() +" " + patientFound.getLastName())
                .doctorName(doctorFound.getFirstName() + " " + doctorFound.getLastName())
                .doctorSpecialization(doctorFound.getSpecialization())
                .appointmentTime(createdApt.getAppointmentTime())
                .appointmentDate(createdApt.getAppointmentDate())
                .appointmentStatus(createdApt.getStatus())
                .build();
    }

    //- View all appointments
    public List<AppointmentEntity> getAllAppointments(){
        return appointmentRepository.findAll();
    }

    //- View an appointment by ID
    public AppointmentResponseDto getOneAppointment(Long id){
        Optional<AppointmentEntity> findOne = appointmentRepository.findById(id);
        if(findOne.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment Not found");
        AppointmentEntity foundApt = findOne.get();
        //get the doctor details
        Optional<DoctorEntity> getDoctor = doctorRepository.findById(foundApt.getDoctorId());
        if(getDoctor.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found");
        DoctorEntity doctorFound = getDoctor.get();
        //get the patient details
        Optional<PatientEntity> getPatient = patientRepository.findById(foundApt.getPatientId());
        if(getPatient.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");
        PatientEntity patientFound = getPatient.get();
        return AppointmentResponseDto.builder()
                .Id(foundApt.getId())
                .patientName(patientFound.getFirstName() + patientFound.getLastName())
                .doctorName(doctorFound.getFirstName() + doctorFound.getLastName())
                .doctorSpecialization(doctorFound.getSpecialization())
                .appointmentDate(foundApt.getAppointmentDate())
                .appointmentTime(foundApt.getAppointmentTime())
                .appointmentStatus(foundApt.getStatus())
                .build();
    }

    //- Cancel an appointment
    public AppointmentEntity cancelAppointment(Long id){
        Optional<AppointmentEntity> getApt = appointmentRepository.findById(id);
        if(getApt.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Appointment not found");
        AppointmentEntity foundApt = getApt.get();
        if(foundApt.getStatus().equals(AppointmentStatus.COMPLETED)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment already completed.");
        foundApt.setStatus(AppointmentStatus.CANCELLED);
        return appointmentRepository.save(foundApt);
    }

    //- Complete an appointment
    public AppointmentEntity completeAppointment(Long id){
        Optional<AppointmentEntity> getApt = appointmentRepository.findById(id);
        if(getApt.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Appointment not found");
        AppointmentEntity foundApt = getApt.get();
        if(foundApt.getStatus().equals(AppointmentStatus.CANCELLED)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment already cancelled.");
        foundApt.setStatus(AppointmentStatus.COMPLETED);
        return appointmentRepository.save(foundApt);
    }

}
