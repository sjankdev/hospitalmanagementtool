package com.demo.hospitalmanagementtool.service.impl;

import com.demo.hospitalmanagementtool.entities.Appointment;
import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.repository.AppointmentRepository;
import com.demo.hospitalmanagementtool.repository.DoctorRepository;
import com.demo.hospitalmanagementtool.service.DoctorAppointmentCalendarService;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public List<Appointment> getAppointmentsForMonth(List<Appointment> appointments, LocalDate firstDayOfMonth, LocalDate lastDayOfMonth) {
        return appointments.stream().filter(appointment -> {
            LocalDate appointmentDate = appointment.getDateTime().toLocalDate();
            return appointmentDate.isAfter(firstDayOfMonth.minusDays(1)) && appointmentDate.isBefore(lastDayOfMonth.plusDays(1));
        }).collect(Collectors.toList());
    }

    @Override
    public void setModelAttributesDoctor(Model model, Doctor doctor, Map<String, List<Appointment>> appointmentsByDate, int year, int month) {
        YearMonth currentMonthYear = YearMonth.of(year, month);

        model.addAttribute("doctor", doctor);
        model.addAttribute("groupedAppointments", appointmentsByDate);
        model.addAttribute("monthYear", currentMonthYear.format(DateTimeFormatter.ofPattern("MMMM yyyy")));
        model.addAttribute("previousYear", currentMonthYear.minusMonths(1).getYear());
        model.addAttribute("previousMonth", currentMonthYear.minusMonths(1).getMonthValue());
        model.addAttribute("nextYear", currentMonthYear.plusMonths(1).getYear());
        model.addAttribute("nextMonth", currentMonthYear.plusMonths(1).getMonthValue());
        model.addAttribute("appointmentsByDate", appointmentsByDate);
    }

    @Override
    public void setModelAttributesAllDoctors(Model model, Map<String, List<Appointment>> appointmentsByDate, int year, int month) {
        YearMonth monthYear = YearMonth.of(year, month);
        model.addAttribute("monthYear", monthYear.format(DateTimeFormatter.ofPattern("MMMM yyyy")));
        model.addAttribute("previousYear", monthYear.minusMonths(1).getYear());
        model.addAttribute("previousMonth", monthYear.minusMonths(1).getMonthValue());
        model.addAttribute("nextYear", monthYear.plusMonths(1).getYear());
        model.addAttribute("nextMonth", monthYear.plusMonths(1).getMonthValue());
        model.addAttribute("appointmentsByDate", appointmentsByDate);
        model.addAttribute("appointmentsByDate", appointmentsByDate);

    }
}



