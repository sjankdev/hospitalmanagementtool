package com.demo.hospitalmanagementtool.service;

import com.demo.hospitalmanagementtool.entities.Appointment;

import java.util.List;

public interface AppointmentService {

    List<Appointment> getAllAppointments();

    Appointment getAppointmentById(Long id);

    void saveAppointment(Appointment appointment);

    void updateAppointment(Long id, Appointment appointment);

    void deleteAppointment(Long id);

    List<Appointment> getApprovedAppointments(int year, int month);

    List<Appointment> filterAppointmentsByMonth(List<Appointment> appointments, int year, int month);


}

