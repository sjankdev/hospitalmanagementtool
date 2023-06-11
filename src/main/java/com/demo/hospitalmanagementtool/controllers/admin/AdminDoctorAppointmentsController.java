package com.demo.hospitalmanagementtool.controllers.admin;

import com.demo.hospitalmanagementtool.entities.Appointment;
import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.repository.AppointmentRepository;
import com.demo.hospitalmanagementtool.service.AppointmentService;
import com.demo.hospitalmanagementtool.service.DoctorAppointmentCalendarService;
import com.demo.hospitalmanagementtool.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Controller
@RequestMapping("/auth-doctorAppointments")
public class AdminDoctorAppointmentsController {

    @Autowired
    private DoctorAppointmentCalendarService calendarService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;


    @GetMapping("/doctor/{doctorId}/appointments")
    public String getDoctorAppointments(@PathVariable Long doctorId, @RequestParam(value = "year", required = false, defaultValue = "2023") int year, @RequestParam(value = "month", required = false, defaultValue = "1") int month, Model model) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        List<Appointment> appointments = calendarService.getAppointmentsByDoctor(doctor);

        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());

        List<Appointment> appointmentsForMonth = calendarService.getAppointmentsForMonth(appointments, firstDayOfMonth, lastDayOfMonth);

        Map<String, List<Appointment>> appointmentsByDate = calendarService.groupAppointmentsByDate(appointmentsForMonth);

        calendarService.setModelAttributesDoctor(model, doctor, appointmentsByDate, year, month);

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

        List<Appointment> approvedAppointments = appointmentService.getApprovedAppointments(year, month);

        List<Appointment> allAppointments = new ArrayList<>(appointments);
        allAppointments.addAll(approvedAppointments);

        Map<String, List<Appointment>> appointmentsByDate = calendarService.groupAppointmentsByDate(allAppointments);

        calendarService.setModelAttributesAllDoctors(model, appointmentsByDate, year, month);

        return "all-doctors-appointments";
    }


}


