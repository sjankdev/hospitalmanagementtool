package com.demo.hospitalmanagementtool.controllers;

import com.demo.hospitalmanagementtool.entities.Appointment;
import com.demo.hospitalmanagementtool.exceptions.NotFoundException;
import com.demo.hospitalmanagementtool.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/list")
    public String getAllAppointments(Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        model.addAttribute("appointments", appointments);
        return "appointments/list";
    }

    @GetMapping("/{id}")
    public String getAppointmentById(@PathVariable("id") Long id, Model model) {
        try {
            Appointment appointment = appointmentService.getAppointmentById(id);
            model.addAttribute("appointment", appointment);
            return "appointments/details";
        } catch (NotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }
    }

    @GetMapping("/create")
    public String createAppointmentForm(Model model) {
        Appointment appointment = new Appointment();
        model.addAttribute("appointment", appointment);
        return "appointments/create";
    }

    @PostMapping("/save")
    public String saveAppointment(@ModelAttribute("appointment") Appointment appointment) {
        appointmentService.saveAppointment(appointment);
        return "redirect:/appointments";
    }

    @GetMapping("/{id}/edit")
    public String editAppointmentForm(@PathVariable("id") Long id, Model model) {
        try {
            Appointment appointment = appointmentService.getAppointmentById(id);
            model.addAttribute("appointment", appointment);
            return "appointments/edit";
        } catch (NotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }
    }

    @PutMapping("/{id}/update")
    public String updateAppointment(@PathVariable("id") Long id, @ModelAttribute("appointment") Appointment appointment) {
        appointmentService.updateAppointment(id, appointment);
        return "redirect:/appointments";
    }

    @DeleteMapping("/{id}")
    public String deleteAppointment(@PathVariable("id") Long id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/appointments";
    }
}
