package com.demo.hospitalmanagementtool.service.impl;

import com.demo.hospitalmanagementtool.entities.MedicalRecord;
import com.demo.hospitalmanagementtool.exceptions.NotFoundException;
import com.demo.hospitalmanagementtool.repository.MedicalRecordRepository;
import com.demo.hospitalmanagementtool.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    @Override
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    @Override
    public MedicalRecord getMedicalRecordById(Long id) {
        return medicalRecordRepository.findById(id).orElseThrow(() -> new NotFoundException("MedicalRecord with ID " + id + " not found."));
    }

    @Override
    public void saveMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordRepository.save(medicalRecord);
    }

    @Override
    public void updateMedicalRecord(Long id, MedicalRecord medicalRecord) {
        MedicalRecord existingMedicalRecord = getMedicalRecordById(id);
        existingMedicalRecord.setPatient(medicalRecord.getPatient());
        existingMedicalRecord.setDoctor(medicalRecord.getDoctor());
        existingMedicalRecord.setAppointment(medicalRecord.getAppointment());
        existingMedicalRecord.setMedicalHistory(medicalRecord.getMedicalHistory());
        existingMedicalRecord.setDiagnosis(medicalRecord.getDiagnosis());
        existingMedicalRecord.setPrescription(medicalRecord.getPrescription());
        existingMedicalRecord.setNotes(medicalRecord.getNotes());
        medicalRecordRepository.save(existingMedicalRecord);
    }

    @Override
    public void deleteMedicalRecord(Long id) {
        medicalRecordRepository.deleteById(id);
    }
}
