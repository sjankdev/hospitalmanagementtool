package com.demo.hospitalmanagementtool.service.impl;

import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.exceptions.NotFoundException;
import com.demo.hospitalmanagementtool.repository.DoctorRepository;
import com.demo.hospitalmanagementtool.security.models.ERole;
import com.demo.hospitalmanagementtool.security.models.Role;
import com.demo.hospitalmanagementtool.security.repository.RoleRepository;
import com.demo.hospitalmanagementtool.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    RoleRepository roleRepository;

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
    public Doctor updateDoctor(Long id, Doctor updatedDoctor) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            doctor.setFirstName(updatedDoctor.getFirstName());
            doctor.setLastName(updatedDoctor.getLastName());
            doctor.setDateOfBirth(updatedDoctor.getDateOfBirth());
            doctor.setGender(updatedDoctor.getGender());
            doctor.setAddress(updatedDoctor.getAddress());
            doctor.setPhoneNumber(updatedDoctor.getPhoneNumber());
            doctor.setEmail(updatedDoctor.getEmail());
            doctor.setSpecialty(updatedDoctor.getSpecialty());
            doctor.setMedicalLicenseNumber(updatedDoctor.getMedicalLicenseNumber());
            doctor.setMedicalSchoolAttended(updatedDoctor.getMedicalSchoolAttended());

            return doctorRepository.save(doctor);
        }
        return null;
    }

    @Override
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    @Override
    public void assignUserRole(Doctor doctor) {
        Role userRole = roleRepository.findByName(ERole.ROLE_DOCTOR).orElseThrow(() -> new RuntimeException("User role not found"));
        doctor.getRoles().add(userRole);
    }

    @Override
    public Optional<Doctor> getDoctorByUsername(String username) {
        return doctorRepository.findByUsername(username);
    }
}

