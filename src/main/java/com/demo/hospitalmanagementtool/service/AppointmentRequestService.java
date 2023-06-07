package com.demo.hospitalmanagementtool.service;

import com.demo.hospitalmanagementtool.entities.AppointmentRequest;

import java.time.LocalDate;
import java.time.LocalTime;

public interface AppointmentRequestService {
    public void createAppointmentRequest(AppointmentRequest appointmentRequest);
}
