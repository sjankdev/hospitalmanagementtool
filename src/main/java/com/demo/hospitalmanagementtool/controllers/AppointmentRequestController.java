package com.demo.hospitalmanagementtool.controllers;


import com.demo.hospitalmanagementtool.entities.AppointmentRequest;
import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.entities.Patient;
import com.demo.hospitalmanagementtool.service.AppointmentRequestApprovalService;
import com.demo.hospitalmanagementtool.service.AppointmentRequestService;
import com.demo.hospitalmanagementtool.service.DoctorService;
import com.demo.hospitalmanagementtool.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/appointment-request")
public class AppointmentRequestController {
    private final AppointmentRequestService appointmentRequestService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final AppointmentRequestApprovalService appointmentRequestApprovalService;

    public AppointmentRequestController(AppointmentRequestService appointmentRequestService, PatientService patientService, DoctorService doctorService, AppointmentRequestApprovalService appointmentRequestApprovalService) {
        this.appointmentRequestService = appointmentRequestService;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.appointmentRequestApprovalService = appointmentRequestApprovalService;
    }

    @GetMapping("/{patientId}/create-request")
    public String showCreateRequestForm(@PathVariable Long patientId, Model model, Principal principal) {
        String username = principal.getName();

        Patient patient = patientService.getPatientById(patientId);

        if (patient != null && patient.getUsername().equals(username)) {
            Doctor doctor = patient.getDoctor();

            model.addAttribute("patient", patient);
            model.addAttribute("doctor", doctor);
            model.addAttribute("appointmentRequest", new AppointmentRequest());

            return "patient/create-appointment-request";
        } else {
            return "error";
        }
    }


    @PostMapping("/{patientId}/create-request")
    public String processCreateRequestForm(@PathVariable Long patientId,
                                           @ModelAttribute("appointmentRequest") AppointmentRequest appointmentRequest) {
        Patient patient = patientService.getPatientById(patientId);
        Doctor doctor = patient.getDoctor();

        appointmentRequest.setPatient(patient);
        appointmentRequest.setDoctor(doctor);

        appointmentRequestService.createAppointmentRequest(appointmentRequest);
        return "redirect:/patients/index";
    }

    @GetMapping("/doctor/{doctorId}/requests")
    public String viewAppointmentRequests(@PathVariable Long doctorId, Model model) {
        Doctor doctor = doctorService.getDoctorById(doctorId);

        if (doctor != null) {
            model.addAttribute("doctor", doctor);
            model.addAttribute("appointmentRequests", appointmentRequestService.getAppointmentRequestsForDoctor(doctor));
            return "doctor/appointment-requests";
        } else {
            return "error";
        }
    }

    @PostMapping("/doctor/{doctorId}/requests/{requestId}/approve")
    public String approveAppointmentRequest(@PathVariable Long doctorId, @PathVariable Long requestId) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        AppointmentRequest appointmentRequest = appointmentRequestService.getAppointmentRequestById(requestId);

        if (appointmentRequest != null && appointmentRequest.getDoctor().equals(doctor)) {
            appointmentRequestApprovalService.approveAppointmentRequest(appointmentRequest);
            return "redirect:/appointment-request/doctor/" + doctorId + "/requests";
        } else {
            return "error";
        }
    }

    @PostMapping("/doctor/{doctorId}/requests/{requestId}/reject")
    public String rejectAppointmentRequest(@PathVariable Long doctorId, @PathVariable Long requestId) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        AppointmentRequest appointmentRequest = appointmentRequestService.getAppointmentRequestById(requestId);

        if (appointmentRequest != null && appointmentRequest.getDoctor().equals(doctor)) {
            appointmentRequestApprovalService.rejectAppointmentRequest(appointmentRequest);
            return "redirect:/appointment-request/doctor/" + doctorId + "/requests";
        } else {
            return "error";
        }
    }
}


