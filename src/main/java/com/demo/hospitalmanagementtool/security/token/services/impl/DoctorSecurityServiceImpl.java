package com.demo.hospitalmanagementtool.security.token.services.impl;

import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.security.token.services.DoctorSecurityService;
import com.demo.hospitalmanagementtool.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class DoctorSecurityServiceImpl implements DoctorSecurityService {

    @Autowired
    DoctorService doctorService;

    public Doctor validateDoctor(Long doctorId, Principal principal) {
        String username = principal.getName();
        Doctor doctor = doctorService.getDoctorById(doctorId);
        if (doctor != null && doctor.getUsername().equals(username)) {
            return doctor;
        }
        return null;
    }

}
