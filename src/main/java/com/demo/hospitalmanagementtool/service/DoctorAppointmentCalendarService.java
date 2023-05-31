package com.demo.hospitalmanagementtool.service;

import com.demo.hospitalmanagementtool.entities.Appointment;
import com.demo.hospitalmanagementtool.entities.Doctor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface DoctorAppointmentCalendarService {

    List<Appointment> getDoctorAppointmentDatesByDoctorId(Long doctorId, int year, int month);

    List<LocalDate> getCalendarDays(int year, int month);

    List<Appointment> getAppointmentsByDoctor(Doctor doctor);

    Map<String, List<Appointment>> groupAppointmentsByDate(List<Appointment> appointments);

}
