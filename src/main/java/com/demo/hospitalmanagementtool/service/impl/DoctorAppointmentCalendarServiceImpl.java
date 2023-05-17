package com.demo.hospitalmanagementtool.service.impl;

import com.demo.hospitalmanagementtool.entities.Appointment;
import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.repository.AppointmentRepository;
import com.demo.hospitalmanagementtool.repository.DoctorRepository;
import com.demo.hospitalmanagementtool.service.DoctorAppointmentCalendarService;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DoctorAppointmentCalendarServiceImpl implements DoctorAppointmentCalendarService {


    private DoctorRepository doctorRepository;
    private AppointmentRepository appointmentRepository;

    public DoctorAppointmentCalendarServiceImpl(DoctorRepository doctorRepository, AppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<Appointment> getDoctorAppointments(Long doctorId) {
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);

        if (doctor == null) {
            throw new IllegalArgumentException("Doctor not found.");
        }

        return appointmentRepository.findByDoctor(doctor);
    }

    @Override
    public List<LocalDate> getCalendarDays() {
        List<LocalDate> calendarDays = new ArrayList<>();

        // Get the current month and year
        YearMonth yearMonth = YearMonth.now();
        int daysInMonth = yearMonth.lengthOfMonth();

        // Generate the calendar days for the current month
        for (int day = 1; day <= daysInMonth; day++) {
            calendarDays.add(yearMonth.atDay(day));
        }

        return calendarDays;
    }

    @Override
    public Map<String, String> getDoctorAppointmentDates(Long doctorId) {
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);

        if (doctor == null) {
            throw new IllegalArgumentException("Doctor not found.");
        }

        List<Appointment> appointments = appointmentRepository.findByDoctor(doctor);

        Map<String, String> appointmentDates = new HashMap<>();

        for (Appointment appointment : appointments) {
            LocalDate date = appointment.getDateTime().toLocalDate();
            String status = appointment.getStatus();
            String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            appointmentDates.put(formattedDate, status);
        }

        return appointmentDates;
    }
}


