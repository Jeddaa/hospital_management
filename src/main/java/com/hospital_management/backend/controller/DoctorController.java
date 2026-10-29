package com.hospital_management.backend.controller;

import com.hospital_management.backend.dto.request.CreateDoctorRequestDto;
import com.hospital_management.backend.dto.request.UpdateDoctorRequestDto;
import com.hospital_management.backend.dto.response.DoctorResponseDto;
import com.hospital_management.backend.entity.DoctorEntity;
import com.hospital_management.backend.service.DoctorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    //@jeddaa: to-do
    //- Create a doctor
    //- View all doctors
    //- View a doctor by ID
    //- Update a doctor
    //- Delete a doctor
    private final DoctorService doctorService;

    DoctorController(DoctorService doctorService){
        this.doctorService = doctorService;
    }

    @PostMapping
    public DoctorResponseDto createDoctor(@RequestBody CreateDoctorRequestDto createData){
        return doctorService.createDoctor(createData);
    }

    @GetMapping()
    public List<DoctorResponseDto> getAllDoctor(){
        return doctorService.getAllDoctor();
    }

    @GetMapping("/{id}")
    public DoctorResponseDto getOneDoctor(@PathVariable() Long id){
        return doctorService.getOneDoctor(id);
    }

    @PatchMapping("/{id}")
    public DoctorResponseDto updateDoctor(@PathVariable Long id, @RequestBody UpdateDoctorRequestDto createData){
        return doctorService.updateOneDoctor(id, createData);
    }

    @DeleteMapping("/{id}")
    public String deleteDoctor(@PathVariable Long id){
        return doctorService.deleteOneDoctor(id);
    }

}
