package com.demo.hospitalmanagementtool.service;

import com.demo.hospitalmanagementtool.entities.Appointment;

import java.util.List;

public interface AppointmentService {

    List<Appointment> getAllAppointments();

    Appointment getAppointmentById(Long id);

    void saveAppointment(Appointment appointment);

    void updateAppointment(Long id, Appointment appointment);

    void deleteAppointment(Long id);

}

