package com.demo.hospitalmanagementtool.repository;

import com.demo.hospitalmanagementtool.entities.AppointmentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRequestRepository extends JpaRepository<AppointmentRequest, Long> {
}
