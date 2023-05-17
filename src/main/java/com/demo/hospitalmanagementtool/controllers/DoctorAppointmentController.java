package com.demo.hospitalmanagementtool.controllers;

import com.demo.hospitalmanagementtool.entities.Appointment;
import com.demo.hospitalmanagementtool.service.DoctorAppointmentCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/doctorAppointments")
public class DoctorAppointmentController {
    private DoctorAppointmentCalendarService calendarService;

    @Autowired
    public DoctorAppointmentController(DoctorAppointmentCalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping("/doctors/{doctorId}/calendar")
    public String displayDoctorAppointmentCalendar(@PathVariable Long doctorId, Model model) {
        Map<String, String> appointmentDates = calendarService.getDoctorAppointmentDates(doctorId);
        List<LocalDate> calendarDays = calendarService.getCalendarDays();
        List<Appointment> appointments = calendarService.getDoctorAppointments(doctorId);
        model.addAttribute("doctorId", doctorId);
        model.addAttribute("calendarDays", calendarDays);
        model.addAttribute("appointments", appointments);
        model.addAttribute("appointmentDates", appointmentDates.keySet());
        model.addAttribute("appointmentStatuses", appointmentDates.values());
        return "doctor-appointment-calendar";
    }
}
