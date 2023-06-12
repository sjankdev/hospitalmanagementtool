package com.demo.hospitalmanagementtool.repository;

import com.demo.hospitalmanagementtool.entities.AppointmentRequest;
import com.demo.hospitalmanagementtool.entities.AppointmentRequestApprovalStatus;
import com.demo.hospitalmanagementtool.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRequestRepository extends JpaRepository<AppointmentRequest, Long> {
    List<AppointmentRequest> findByDoctor(Doctor doctor);

    List<AppointmentRequest> findByDoctorAndAppointmentRequestApprovalStatus(Doctor doctor, AppointmentRequestApprovalStatus status);

    List<AppointmentRequest> findByAppointmentRequestApprovalStatusAndDateTimeBetween(
            AppointmentRequestApprovalStatus approvalStatus,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime
    );

    List<AppointmentRequest> findByAppointmentRequestApprovalStatus(AppointmentRequestApprovalStatus approvalStatus);

}

