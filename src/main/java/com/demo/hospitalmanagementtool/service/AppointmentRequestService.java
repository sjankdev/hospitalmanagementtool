package com.demo.hospitalmanagementtool.service;

import com.demo.hospitalmanagementtool.entities.AppointmentRequest;
import com.demo.hospitalmanagementtool.entities.Doctor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRequestService {
    public void createAppointmentRequest(AppointmentRequest appointmentRequest);

    List<AppointmentRequest> getAppointmentRequestsForDoctor(Doctor doctor);
    AppointmentRequest getAppointmentRequestById(Long id);

}
