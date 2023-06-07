package com.demo.hospitalmanagementtool.service;

import com.demo.hospitalmanagementtool.entities.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {


    List<Patient> getAllPatients();

    Patient getPatientById(Long id);

    void savePatient(Patient patient);

    void updatePatient(Long id, Patient patient);

    void deletePatient(Long id);

    void assignDoctorToPatient(Long patientId, Long doctorId);

    void assignUserRole(Patient patient);

    public Optional<Patient> getPatientByUsername(String username);

}
