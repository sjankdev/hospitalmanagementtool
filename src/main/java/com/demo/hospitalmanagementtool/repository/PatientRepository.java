package com.demo.hospitalmanagementtool.repository;

import com.demo.hospitalmanagementtool.entities.Patient;
import com.demo.hospitalmanagementtool.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
