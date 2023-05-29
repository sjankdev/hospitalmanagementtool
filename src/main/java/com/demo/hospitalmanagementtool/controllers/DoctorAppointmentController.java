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
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;

    public DoctorAppointmentController(DoctorAppointmentCalendarService calendarService, DoctorRepository doctorRepository, AppointmentRepository appointmentRepository, AppointmentService appointmentService, DoctorService doctorService) {
        this.calendarService = calendarService;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
    }

  /* @GetMapping("/doctor-appointment-calendar/{doctorId}")
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
    }*/

    @GetMapping("/doctor/{doctorId}/appointments")
    public String getDoctorAppointments(@PathVariable Long doctorId, Model model) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctor);

        Map<String, List<Appointment>> groupedAppointments = appointmentService.groupAppointmentsByDate(appointments);
        model.addAttribute("doctor", doctor);
        model.addAttribute("groupedAppointments", groupedAppointments);

        return "doctor-appointment-calendar";
    }

    @GetMapping("/allEvents")
    public String getAllEvents(
            @RequestParam(value = "year", required = false, defaultValue = "2023") int year,
            @RequestParam(value = "month", required = false, defaultValue = "1") int month,
            Model model
    ) {
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());

        List<Appointment> appointments = appointmentRepository.findByDateTimeBetween(
                firstDayOfMonth.atStartOfDay(),
                lastDayOfMonth.atTime(LocalTime.MAX)
        );

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


