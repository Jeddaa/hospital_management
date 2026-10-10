package com.hospital_management.backend.controller;

import com.hospital_management.backend.dto.request.AppointmentRequestDto;
import com.hospital_management.backend.dto.response.AppointmentResponseDto;
import com.hospital_management.backend.entity.AppointmentEntity;
import com.hospital_management.backend.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;

    AppointmentController(AppointmentService appointmentService){
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public AppointmentResponseDto createAppointment(@RequestBody AppointmentRequestDto createData){
        return appointmentService.createAppointment(createData);
    }

    @GetMapping()
    public List<AppointmentEntity> getOneAppointment(){
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/{id}")
    public AppointmentResponseDto getOneAppointment(@PathVariable() Long id){
        return appointmentService.getOneAppointment(id);
    }

    @PatchMapping("/{id}/cancel")
    public AppointmentEntity cancelAppointment(@PathVariable Long id){
        return appointmentService.cancelAppointment(id);
    }
    @PatchMapping("/{id}/complete")
    public AppointmentEntity completeAppointment(@PathVariable Long id){
        return appointmentService.completeAppointment(id);
    }

}
