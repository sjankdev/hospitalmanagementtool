package com.demo.hospitalmanagementtool.service;

import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.entities.Patient;

import java.util.List;
import java.util.Optional;

public interface DoctorService {

    List<Doctor> getAllDoctors();

    Doctor getDoctorById(Long id);

    void saveDoctor(Doctor doctor);

    Doctor updateDoctor(Long id, Doctor updatedDoctor);

    void deleteDoctor(Long id);

    void assignUserRole(Doctor doctor);

    Optional<Doctor> getDoctorByUsername(String username);


}

