package com.demo.hospitalmanagementtool.service;

import com.demo.hospitalmanagementtool.entities.Patient;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface PatientService {

    List<Patient> getAllPatients();

    Patient getPatientById(Long id);

    void savePatient(Patient patient);

    void updatePatient(Long id, Patient patient);

    void deletePatient(Long id);

}
