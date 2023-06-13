package com.demo.hospitalmanagementtool.security.token.services.impl;

import com.demo.hospitalmanagementtool.entities.Patient;
import com.demo.hospitalmanagementtool.security.token.services.PatientSecurityService;
import com.demo.hospitalmanagementtool.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class PatientSecurityServiceImpl implements PatientSecurityService {

    @Autowired
    PatientService patientService;

    public Patient validatePatient(Long patientId, Principal principal) {
        String username = principal.getName();
        Patient patient = patientService.getPatientById(patientId);
        if (patient != null && patient.getUsername().equals(username)) {
            return patient;
        }
        return null;
    }

}
