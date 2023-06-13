package com.demo.hospitalmanagementtool.security.token.services;

import com.demo.hospitalmanagementtool.entities.Patient;

import java.security.Principal;

public interface PatientSecurityService {

    Patient validatePatient(Long patientId, Principal principal);

}