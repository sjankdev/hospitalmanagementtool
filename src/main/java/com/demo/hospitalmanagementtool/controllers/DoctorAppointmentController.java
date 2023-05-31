package com.demo.hospitalmanagementtool.controllers;

import com.demo.hospitalmanagementtool.entities.Appointment;
import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.repository.AppointmentRepository;
import com.demo.hospitalmanagementtool.repository.DoctorRepository;
import com.demo.hospitalmanagementtool.service.AppointmentService;
import com.demo.hospitalmanagementtool.service.DoctorAppointmentCalendarService;
import com.demo.hospitalmanagementtool.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

@Controller
@RequestMapping("/doctorAppointments")
public class DoctorAppointmentController {
    private final DoctorAppointmentCalendarService calendarService;
    private final AppointmentRepository appointmentRepository;
    private final DoctorService doctorService;

    public DoctorAppointmentController(DoctorAppointmentCalendarService calendarService, AppointmentRepository appointmentRepository, DoctorService doctorService) {
        this.calendarService = calendarService;
        this.appointmentRepository = appointmentRepository;
        this.doctorService = doctorService;
    }

    @GetMapping("/doctor/{doctorId}/appointments")
    public String getDoctorAppointments(@PathVariable Long doctorId, @RequestParam(value = "year", required = false, defaultValue = "2023") int year, @RequestParam(value = "month", required = false, defaultValue = "1") int month, Model model) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        List<Appointment> appointments = calendarService.getAppointmentsByDoctor(doctor);

        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());

        List<Appointment> appointmentsForMonth = calendarService.getAppointmentsForMonth(appointments, firstDayOfMonth, lastDayOfMonth);

        Map<String, List<Appointment>> appointmentsByDate = calendarService.groupAppointmentsByDate(appointmentsForMonth);

        calendarService.setModelAttributes(model, doctor, appointmentsByDate, year, month);

        return "doctor-appointment-calendar";
    }


    @GetMapping("/allEvents")
    public String getAllEvents(@RequestParam(value = "year", required = false, defaultValue = "2023") int year, @RequestParam(value = "month", required = false, defaultValue = "1") int month, Model model) {
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());

        List<Appointment> appointments = appointmentRepository.findByDateTimeBetween(firstDayOfMonth.atStartOfDay(), lastDayOfMonth.atTime(LocalTime.MAX));

        Map<String, List<Appointment>> appointmentsByDate = new LinkedHashMap<>();

        for (Appointment appointment : appointments) {
            LocalDate appointmentDate = appointment.getDateTime().toLocalDate();
            String dateStr = appointmentDate.toString();
            List<Appointment> dateAppointments = appointmentsByDate.computeIfAbsent(dateStr, k -> new ArrayList<>());
            dateAppointments.add(appointment);
        }

        model.addAttribute("monthYear", YearMonth.of(year, month).format(DateTimeFormatter.ofPattern("MMMM yyyy")));
        model.addAttribute("previousYear", YearMonth.of(year, month).minusMonths(1).getYear());
        model.addAttribute("previousMonth", YearMonth.of(year, month).minusMonths(1).getMonthValue());
        model.addAttribute("nextYear", YearMonth.of(year, month).plusMonths(1).getYear());
        model.addAttribute("nextMonth", YearMonth.of(year, month).plusMonths(1).getMonthValue());
        model.addAttribute("appointmentsByDate", appointmentsByDate);

        return "all-doctors-appointments";
    }


}


