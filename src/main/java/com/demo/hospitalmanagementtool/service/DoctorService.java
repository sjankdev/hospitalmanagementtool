package com.demo.hospitalmanagementtool.service;

import com.demo.hospitalmanagementtool.entities.Doctor;

import java.util.List;

public interface DoctorService {

    List<Doctor> getAllDoctors();

    Doctor getDoctorById(Long id);

    void saveDoctor(Doctor doctor);

    Doctor updateDoctor(Long id, Doctor updatedDoctor);

    void deleteDoctor(Long id);

}

