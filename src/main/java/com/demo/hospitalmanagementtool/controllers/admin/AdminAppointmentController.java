package com.demo.hospitalmanagementtool.controllers.admin;

import com.demo.hospitalmanagementtool.entities.Appointment;
import com.demo.hospitalmanagementtool.entities.AppointmentRequest;
import com.demo.hospitalmanagementtool.exceptions.NotFoundException;
import com.demo.hospitalmanagementtool.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/auth-appointments")
public class AdminAppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private StaffService staffService;

    @Autowired
    AppointmentRequestService appointmentRequestService;

    @GetMapping("/list")
    public String getAllAppointments(Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        model.addAttribute("appointments", appointments);
        return "appointments/list";
    }

    @GetMapping("/approved")
    public String getAllApprovedAppointments(Model model) {
        List<AppointmentRequest> approvedAppointments = appointmentRequestService.getAllApprovedAppointments();
        model.addAttribute("appointments", approvedAppointments);
        return "appointments/approved-appointments";
    }

    @GetMapping("/{id}/details")
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
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("staff", staffService.getAllStaff());

        return "appointments/create";
    }

    @PostMapping("/save")
    public String saveAppointment(@ModelAttribute("appointment") Appointment appointment) {
        appointmentService.saveAppointment(appointment);
        return "redirect:/auth-appointments/list";
    }

    @GetMapping("/{id}/edit")
    public String editAppointmentForm(@PathVariable("id") Long id, Model model) {
        try {
            Appointment appointment = appointmentService.getAppointmentById(id);
            model.addAttribute("appointment", appointment);
            model.addAttribute("doctors", doctorService.getAllDoctors());
            model.addAttribute("patients", patientService.getAllPatients());
            model.addAttribute("staff", staffService.getAllStaff());
            return "appointments/edit";
        } catch (NotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }
    }

    @PostMapping("/{id}/update")
    public String updateAppointment(@PathVariable("id") Long id, @ModelAttribute("appointment") Appointment appointment) {
        appointmentService.updateAppointment(id, appointment);
        return "redirect:/auth-appointments/list";
    }

    @PostMapping("/{id}/delete")
    public String deleteAppointment(@PathVariable("id") Long id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/auth-appointments/list";
    }
}