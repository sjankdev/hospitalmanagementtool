package com.demo.hospitalmanagementtool.repository;

import com.demo.hospitalmanagementtool.entities.AppointmentRequest;
import com.demo.hospitalmanagementtool.entities.AppointmentRequestApprovalStatus;
import com.demo.hospitalmanagementtool.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRequestRepository extends JpaRepository<AppointmentRequest, Long> {
    List<AppointmentRequest> findByDoctor(Doctor doctor);

    List<AppointmentRequest> findByDoctorAndAppointmentRequestApprovalStatus(Doctor doctor, AppointmentRequestApprovalStatus status);

}
