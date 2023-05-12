package com.demo.hospitalmanagementtool.service.impl;

import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.exceptions.NotFoundException;
import com.demo.hospitalmanagementtool.repository.DoctorRepository;
import com.demo.hospitalmanagementtool.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

@Autowired
    DoctorRepository doctorRepository;

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor with ID " + id + " not found."));
    }

    @Override
    public void saveDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    @Override
    public void updateDoctor(Long id, Doctor doctor) {
        Doctor existingDoctor = getDoctorById(id);
        existingDoctor.setFirstName(doctor.getFirstName());
        existingDoctor.setLastName(doctor.getLastName());
        existingDoctor.setDateOfBirth(doctor.getDateOfBirth());
        existingDoctor.setGender(doctor.getGender());
        existingDoctor.setAddress(doctor.getAddress());
        existingDoctor.setPhoneNumber(doctor.getPhoneNumber());
        existingDoctor.setEmail(doctor.getEmail());
        existingDoctor.setSpecialty(doctor.getSpecialty());
        doctorRepository.save(existingDoctor);
    }

    @Override
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
}

