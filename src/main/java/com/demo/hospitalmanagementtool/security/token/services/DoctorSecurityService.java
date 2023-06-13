package com.demo.hospitalmanagementtool.security.token.services;

import com.demo.hospitalmanagementtool.entities.Doctor;

import java.security.Principal;

public interface DoctorSecurityService {

    Doctor validateDoctor(Long doctorId, Principal principal);

}