package com.demo.hospitalmanagementtool.service;

import com.demo.hospitalmanagementtool.entities.Appointment;
import com.demo.hospitalmanagementtool.entities.Doctor;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface DoctorAppointmentCalendarService {

    List<Appointment> getAppointmentsByDoctor(Doctor doctor);

    Map<String, List<Appointment>> groupAppointmentsByDate(List<Appointment> appointments);

    List<Appointment> getAppointmentsForMonth(List<Appointment> appointments, LocalDate firstDayOfMonth, LocalDate lastDayOfMonth);

    void setModelAttributes(Model model, Doctor doctor, Map<String, List<Appointment>> appointmentsByDate, int year, int month);

}
