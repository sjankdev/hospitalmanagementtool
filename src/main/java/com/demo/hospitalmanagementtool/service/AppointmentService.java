package com.demo.hospitalmanagementtool.service;

import com.demo.hospitalmanagementtool.entities.Appointment;
import com.demo.hospitalmanagementtool.entities.Doctor;

import java.util.List;
import java.util.Map;

public interface AppointmentService {

    List<Appointment> getAllAppointments();

    Appointment getAppointmentById(Long id);

    void saveAppointment(Appointment appointment);

    void updateAppointment(Long id, Appointment appointment);

    void deleteAppointment(Long id);


    List<Appointment> getAppointmentsByDoctor(Doctor doctor);

    Map<String, List<Appointment>> groupAppointmentsByDate(List<Appointment> appointments);
}

