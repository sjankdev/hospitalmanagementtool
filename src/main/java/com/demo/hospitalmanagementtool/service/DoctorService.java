package com.demo.hospitalmanagementtool.service;

import com.demo.hospitalmanagementtool.entities.Doctor;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface DoctorService {

    List<Doctor> getAllDoctors();

    Doctor getDoctorById(Long id) ;

    void saveDoctor(Doctor doctor);

    void updateDoctor(Long id, Doctor doctor);

    void deleteDoctor(Long id);

}

