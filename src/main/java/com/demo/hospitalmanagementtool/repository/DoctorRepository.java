package com.demo.hospitalmanagementtool.repository;

import com.demo.hospitalmanagementtool.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
