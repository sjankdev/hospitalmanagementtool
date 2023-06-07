package com.demo.hospitalmanagementtool.service;

import com.demo.hospitalmanagementtool.entities.AppointmentRequest;

import java.time.LocalDate;
import java.time.LocalTime;

public interface AppointmentRequestService {
    AppointmentRequest createAppointmentRequest(Long patientId, Long doctorId, LocalDate date, LocalTime time);
}
