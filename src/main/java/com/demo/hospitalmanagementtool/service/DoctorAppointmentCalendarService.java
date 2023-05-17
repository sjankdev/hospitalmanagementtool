package com.demo.hospitalmanagementtool.service;

import com.demo.hospitalmanagementtool.entities.Appointment;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface DoctorAppointmentCalendarService {

    public List<Appointment> getDoctorAppointments(Long doctorId);

    public List<LocalDate> getCalendarDays();

    public Map<String, String> getDoctorAppointmentDates(Long doctorId);


}
