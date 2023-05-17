package com.demo.hospitalmanagementtool.controllers;

import com.demo.hospitalmanagementtool.entities.Appointment;
import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.repository.DoctorRepository;
import com.demo.hospitalmanagementtool.service.DoctorAppointmentCalendarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/doctorAppointments")
public class DoctorAppointmentController {
    private final DoctorAppointmentCalendarService calendarService;
    private final DoctorRepository doctorRepository;

    public DoctorAppointmentController(DoctorAppointmentCalendarService calendarService, DoctorRepository doctorRepository) {
        this.calendarService = calendarService;
        this.doctorRepository = doctorRepository;
    }

    @GetMapping("/doctor-appointment-calendar/{doctorId}")
    public String getDoctorAppointmentCalendar(
            @PathVariable("doctorId") Long doctorId,
            @RequestParam(value = "year", required = false, defaultValue = "2023") int year,
            @RequestParam(value = "month", required = false, defaultValue = "1") int month,
            Model model
    ) {
        List<Appointment> doctorAppointments = calendarService.getDoctorAppointmentDatesByDoctorId(doctorId, year, month);

        List<LocalDate> calendarDays = calendarService.getCalendarDays(year, month);

        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        Doctor doctor = doctorOptional.orElseThrow(() -> new IllegalArgumentException("Doctor not found."));

        model.addAttribute("doctorId", doctorId);
        model.addAttribute("doctor", doctor);
        model.addAttribute("calendarDays", calendarDays);
        model.addAttribute("appointments", doctorAppointments);
        model.addAttribute("monthYear", YearMonth.of(year, month).format(DateTimeFormatter.ofPattern("MMMM yyyy")));

        YearMonth currentMonth = YearMonth.of(year, month);
        YearMonth previousMonth = currentMonth.minusMonths(1);
        YearMonth nextMonth = currentMonth.plusMonths(1);

        model.addAttribute("previousYear", previousMonth.getYear());
        model.addAttribute("previousMonth", previousMonth.getMonthValue());
        model.addAttribute("nextYear", nextMonth.getYear());
        model.addAttribute("nextMonth", nextMonth.getMonthValue());

        return "doctor-appointment-calendar";
    }

    @GetMapping("/doctor-appointment-calendar")
    public String getAllDoctorAppointments(
            @RequestParam(value = "year", required = false, defaultValue = "2023") int year,
            @RequestParam(value = "month", required = false, defaultValue = "1") int month,
            Model model
    ) {
        List<Doctor> doctors = doctorRepository.findAll();
        Map<Doctor, List<Appointment>> doctorAppointmentsMap = new HashMap<>();

        for (Doctor doctor : doctors) {
            List<Appointment> doctorAppointments = calendarService.getDoctorAppointmentDatesByDoctorId(doctor.getId(), year, month);
            doctorAppointmentsMap.put(doctor, doctorAppointments);
        }

        List<LocalDate> calendarDays = calendarService.getCalendarDays(year, month);

        model.addAttribute("doctorAppointmentsMap", doctorAppointmentsMap);
        model.addAttribute("calendarDays", calendarDays);
        model.addAttribute("monthYear", YearMonth.of(year, month).format(DateTimeFormatter.ofPattern("MMMM yyyy")));

        YearMonth currentMonth = YearMonth.of(year, month);
        YearMonth previousMonth = currentMonth.minusMonths(1);
        YearMonth nextMonth = currentMonth.plusMonths(1);

        model.addAttribute("previousYear", previousMonth.getYear());
        model.addAttribute("previousMonth", previousMonth.getMonthValue());
        model.addAttribute("nextYear", nextMonth.getYear());
        model.addAttribute("nextMonth", nextMonth.getMonthValue());

        return "all-doctors-appointments";
    }
}


