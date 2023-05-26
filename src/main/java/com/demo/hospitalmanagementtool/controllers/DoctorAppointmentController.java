package com.demo.hospitalmanagementtool.controllers;

import com.demo.hospitalmanagementtool.entities.Appointment;
import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.repository.AppointmentRepository;
import com.demo.hospitalmanagementtool.repository.DoctorRepository;
import com.demo.hospitalmanagementtool.service.DoctorAppointmentCalendarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

@Controller
@RequestMapping("/doctorAppointments")
public class DoctorAppointmentController {
    private final DoctorAppointmentCalendarService calendarService;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    public DoctorAppointmentController(DoctorAppointmentCalendarService calendarService, DoctorRepository doctorRepository, AppointmentRepository appointmentRepository) {
        this.calendarService = calendarService;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/doctor-appointment-calendar/{doctorId}")
    public String getDoctorAppointmentCalendar(@PathVariable("doctorId") Long doctorId, @RequestParam(value = "year", required = false, defaultValue = "2023") int year, @RequestParam(value = "month", required = false, defaultValue = "1") int month, Model model) {
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

    @GetMapping("/events")
    public String getAllEvents(Model model) {
        // Fetch all appointments
        List<Appointment> appointments = appointmentRepository.findAll();

        // Create a map to hold appointments grouped by date
        Map<LocalDate, List<Appointment>> appointmentsByDate = new HashMap<>();

        // Group appointments by date
        for (Appointment appointment : appointments) {
            LocalDate appointmentDate = appointment.getDateTime().toLocalDate();
            List<Appointment> dateAppointments = appointmentsByDate.getOrDefault(appointmentDate, new ArrayList<>());
            dateAppointments.add(appointment);
            appointmentsByDate.put(appointmentDate, dateAppointments);
        }

        model.addAttribute("appointmentsByDate", appointmentsByDate);
        return "all-doctors-appointments";
    }


}


