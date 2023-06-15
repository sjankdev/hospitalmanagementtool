package com.demo.hospitalmanagementtool.service;

import com.demo.hospitalmanagementtool.entities.Appointment;
import com.demo.hospitalmanagementtool.entities.AppointmentRequest;
import com.demo.hospitalmanagementtool.entities.Doctor;

import java.util.List;

public interface AppointmentRequestService {

    void createAppointmentRequest(AppointmentRequest appointmentRequest);

    List<AppointmentRequest> getAppointmentRequestsForDoctor(Doctor doctor);

    AppointmentRequest getAppointmentRequestById(Long id);

    List<AppointmentRequest> getAllApprovedAppointments();

    List<AppointmentRequest> getAllAppointmentRequests();

    void updateRequestAppointment(Long id, AppointmentRequest appointmentRequest);

    void deleteRequestAppointment(Long id);

    List<Appointment> getApprovedAppointmentsForDoctor(Doctor doctor);

    List<Appointment> getAppointmentsByDoctor(Doctor doctor);

    List<AppointmentRequest> getApprovedAppointmentRequests();
}



