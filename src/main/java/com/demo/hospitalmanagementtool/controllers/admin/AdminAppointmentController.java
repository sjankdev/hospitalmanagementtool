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
        List<AppointmentRequest> approvedAppointments = appointmentRequestService.getAllApprovedAppointments();
        model.addAttribute("approvedAppointments", approvedAppointments);
        model.addAttribute("appointments", appointments);
        return "appointments/list";
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

    @GetMapping("/{id}/approved-appointment-details")
    public String getApprovedAppointmentById(@PathVariable("id") Long id, Model model) {
        try {
            AppointmentRequest appointmentRequest = appointmentRequestService.getAppointmentRequestById(id);
            model.addAttribute("appointmentRequest", appointmentRequest);
            return "appointments/approved-appointment-details";
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

    @GetMapping("/{id}/edit-approved-appointment")
    public String editApprovedAppointmentForm(@PathVariable("id") Long id, Model model) {
        try {
            AppointmentRequest appointmentRequest = appointmentRequestService.getAppointmentRequestById(id);
            model.addAttribute("appointmentRequest", appointmentRequest);
            return "appointments/edit-approved-request";
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

    @PostMapping("/{id}/update-approved-request")
    public String updateApprovedAppointment(@PathVariable("id") Long id, @ModelAttribute("appointmentRequest") AppointmentRequest appointmentRequest) {
        appointmentRequestService.updateRequestAppointment(id, appointmentRequest);
        return "redirect:/auth-appointments/list";
    }

    @PostMapping("/{id}/delete")
    public String deleteAppointment(@PathVariable("id") Long id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/auth-appointments/list";
    }

    @PostMapping("/{id}/delete-approved-appointment")
    public String deleteApprovedAppointment(@PathVariable("id") Long id) {
        appointmentRequestService.deleteRequestAppointment(id);
        return "redirect:/auth-appointments/list";
    }

    @GetMapping("/appointment-requests")
    public String getAllAppointmentRequests(Model model) {
        List<AppointmentRequest> appointmentRequests = appointmentRequestService.getAllAppointmentRequests();
        model.addAttribute("appointmentRequests", appointmentRequests);
        return "appointments/appointment-requests";
    }
}