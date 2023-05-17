package com.demo.hospitalmanagementtool.repository;

import com.demo.hospitalmanagementtool.entities.Appointment;
import com.demo.hospitalmanagementtool.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctor(Optional<Doctor> doctor);

    List<Appointment> findByDoctorAndDateTimeBetween(Doctor doctor, LocalDateTime startDateTime, LocalDateTime endDateTime);

}