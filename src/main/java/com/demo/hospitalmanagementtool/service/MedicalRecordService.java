package com.demo.hospitalmanagementtool.service;

import com.demo.hospitalmanagementtool.entities.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {

    List<MedicalRecord> getAllMedicalRecords();

    MedicalRecord getMedicalRecordById(Long id);

    void saveMedicalRecord(MedicalRecord medicalRecord);

    void updateMedicalRecord(Long id, MedicalRecord medicalRecord);

    void deleteMedicalRecord(Long id);

}


