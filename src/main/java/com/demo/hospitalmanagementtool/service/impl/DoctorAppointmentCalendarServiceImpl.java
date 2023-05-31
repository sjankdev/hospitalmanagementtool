package com.demo.hospitalmanagementtool.service.impl;

import com.demo.hospitalmanagementtool.entities.Appointment;
import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.repository.AppointmentRepository;
import com.demo.hospitalmanagementtool.repository.DoctorRepository;
import com.demo.hospitalmanagementtool.service.DoctorAppointmentCalendarService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class DoctorAppointmentCalendarServiceImpl implements DoctorAppointmentCalendarService {


    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public DoctorAppointmentCalendarServiceImpl(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public List<Appointment> getDoctorAppointmentDatesByDoctorId(Long doctorId, int year, int month) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        Doctor doctor = doctorOptional.orElseThrow(() -> new IllegalArgumentException("Doctor not found."));

        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.with(TemporalAdjusters.lastDayOfMonth());

        return appointmentRepository.findByDoctorAndDateTimeBetween(doctor, startOfMonth.atStartOfDay(), endOfMonth.atTime(LocalTime.MAX));
    }

    @Override
    public List<LocalDate> getCalendarDays(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        List<LocalDate> calendarDays = new ArrayList<>();
        for (int day = 1; day <= daysInMonth; day++) {
            calendarDays.add(yearMonth.atDay(day));
        }
        return calendarDays;
    }

    @Override
    public List<Appointment> getAppointmentsByDoctor(Doctor doctor) {
        return appointmentRepository.findByDoctor(Optional.ofNullable(doctor));
    }

    @Override
    public Map<String, List<Appointment>> groupAppointmentsByDate(List<Appointment> appointments) {
        Map<String, List<Appointment>> groupedAppointments = new HashMap<>();

        for (Appointment appointment : appointments) {
            String formattedDate = appointment.getDateTime().toLocalDate().format(dateFormatter);
            List<Appointment> appointmentsByDate = groupedAppointments.getOrDefault(formattedDate, new ArrayList<>());
            appointmentsByDate.add(appointment);
            groupedAppointments.put(formattedDate, appointmentsByDate);
        }

        return groupedAppointments;
    }
}



