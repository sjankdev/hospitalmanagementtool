package com.demo.hospitalmanagementtool.controllers;

import com.demo.hospitalmanagementtool.entities.Appointment;
import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.repository.DoctorRepository;
import com.demo.hospitalmanagementtool.service.DoctorAppointmentCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        // Get doctor appointments for the specified doctor and month
        List<Appointment> doctorAppointments = calendarService.getDoctorAppointmentDatesByDoctorId(doctorId, year, month);

        // Get calendar days for the specified month
        List<LocalDate> calendarDays = calendarService.getCalendarDays(year, month);

        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        Doctor doctor = doctorOptional.orElseThrow(() -> new IllegalArgumentException("Doctor not found."));


        // Prepare data for rendering in the Thymeleaf template
        model.addAttribute("doctorId", doctorId);
        model.addAttribute("doctor", doctor);
        model.addAttribute("calendarDays", calendarDays);
        model.addAttribute("appointments", doctorAppointments);
        model.addAttribute("monthYear", YearMonth.of(year, month).format(DateTimeFormatter.ofPattern("MMMM yyyy")));

        // Calculate previous and next month values for navigation
        YearMonth currentMonth = YearMonth.of(year, month);
        YearMonth previousMonth = currentMonth.minusMonths(1);
        YearMonth nextMonth = currentMonth.plusMonths(1);

        model.addAttribute("previousYear", previousMonth.getYear());
        model.addAttribute("previousMonth", previousMonth.getMonthValue());
        model.addAttribute("nextYear", nextMonth.getYear());
        model.addAttribute("nextMonth", nextMonth.getMonthValue());

        return "doctor-appointment-calendar";
    }


}
