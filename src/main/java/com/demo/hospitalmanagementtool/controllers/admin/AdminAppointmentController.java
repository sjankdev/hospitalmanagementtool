package com.demo.hospitalmanagementtool.controllers.admin;

import com.demo.hospitalmanagementtool.entities.Appointment;
import com.demo.hospitalmanagementtool.entities.AppointmentRequest;
import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.exceptions.NotFoundException;
import com.demo.hospitalmanagementtool.security.token.services.DoctorSecurityService;
import com.demo.hospitalmanagementtool.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
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

    @Autowired
    DoctorSecurityService doctorSecurityService;

    @GetMapping("/list")
    public String getAllAppointments(Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        List<AppointmentRequest> approvedAppointments = appointmentRequestService.getAllApprovedAppointments();
        model.addAttribute("approvedAppointments", approvedAppointments);
        model.addAttribute("appointments", appointments);
        return "admin/appointments/list";
    }


    @GetMapping("/{id}/details")
    public String getAppointmentById(@PathVariable("id") Long id, Model model) {
        try {
            Appointment appointment = appointmentService.getAppointmentById(id);
            model.addAttribute("appointment", appointment);
            return "admin/appointments/details";
        } catch (NotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }
    }

    @GetMapping("/{id}/appointment-request-details")
    public String getAppointmentRequestById(@PathVariable("id") Long id, Model model) {
        try {
            AppointmentRequest appointmentRequest = appointmentRequestService.getAppointmentRequestById(id);
            model.addAttribute("appointmentRequest", appointmentRequest);
            return "admin/appointment-requests/appointment-request-details";
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

        return "admin/appointments/create";
    }

    @PostMapping("/save")
    public String saveAppointment(@ModelAttribute("appointment") @Valid Appointment appointment, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("appointment", appointment);
            model.addAttribute("doctors", doctorService.getAllDoctors());
            model.addAttribute("patients", patientService.getAllPatients());
            model.addAttribute("staff", staffService.getAllStaff());
            return "admin/appointments/create";
        }

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
            return "admin/appointments/edit";
        } catch (NotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }
    }

    @GetMapping("/{id}/edit-appointment-request")
    public String editAppointmentRequestForm(@PathVariable("id") Long id, Model model) {
        try {
            AppointmentRequest appointmentRequest = appointmentRequestService.getAppointmentRequestById(id);
            model.addAttribute("appointmentRequest", appointmentRequest);
            return "admin/appointment-requests/edit-appointment-request";
        } catch (NotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }
    }

    @PostMapping("/{id}/update")
    public String updateAppointment(@PathVariable("id") Long id, @ModelAttribute("appointment") @Valid Appointment appointment, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("appointment", appointment);
            model.addAttribute("doctors", doctorService.getAllDoctors());
            model.addAttribute("patients", patientService.getAllPatients());
            model.addAttribute("staff", staffService.getAllStaff());
            return "admin/appointments/edit";
        }
        appointmentService.updateAppointment(id, appointment);
        return "redirect:/auth-appointments/list";
    }

    @PostMapping("/{id}/update-appointment-request")
    public String updateAppointmentRequest(@PathVariable("id") Long id, @ModelAttribute("appointmentRequest") AppointmentRequest appointmentRequest, Principal principal) {
        appointmentRequestService.updateRequestAppointment(id, appointmentRequest);

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Doctor doctor = doctorSecurityService.validateDoctor(id, principal);

        boolean isDoctor = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_DOCTOR"));

        if (isDoctor) {
            Long doctorId = doctor.getId();
            return "redirect:/doctor/" + doctorId + "/appointments";
        } else {
            return "redirect:/auth-appointments/list";
        }
    }


    @PostMapping("/{id}/delete")
    public String deleteAppointment(@PathVariable("id") Long id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/auth-appointments/list";
    }

    @PostMapping("/{id}/delete-appointment-request")
    public String deleteAppointmentRequest(@PathVariable("id") Long id) {
        appointmentRequestService.deleteRequestAppointment(id);
        return "redirect:/auth-appointments/list";
    }


}