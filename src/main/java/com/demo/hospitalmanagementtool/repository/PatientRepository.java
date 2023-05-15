package com.demo.hospitalmanagementtool.repository;

import com.demo.hospitalmanagementtool.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
