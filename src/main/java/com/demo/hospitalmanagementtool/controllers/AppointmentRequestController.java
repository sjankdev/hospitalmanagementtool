package com.demo.hospitalmanagementtool.controllers;


import com.demo.hospitalmanagementtool.entities.AppointmentRequest;
import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.service.AppointmentRequestService;
import com.demo.hospitalmanagementtool.service.DoctorService;
import com.demo.hospitalmanagementtool.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/appointment-request")
public class AppointmentRequestController {
    private final AppointmentRequestService appointmentRequestService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public AppointmentRequestController(AppointmentRequestService appointmentRequestService,
                                        PatientService patientService,
                                        DoctorService doctorService) {
        this.appointmentRequestService = appointmentRequestService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @GetMapping("/{patientId}/create-request")
    public String showCreateRequestForm(@PathVariable Long patientId, Model model) {
        model.addAttribute("patient", patientService.getPatientById(patientId));
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("appointmentRequest", new AppointmentRequest());
        return "patient/create-appointment-request";
    }

    @PostMapping("/{patientId}/create-request")
    public String processCreateRequestForm(@PathVariable Long patientId,
                                           @ModelAttribute AppointmentRequest appointmentRequest) {
        appointmentRequestService.createAppointmentRequest(patientId, appointmentRequest.getDoctor().getId(),
                appointmentRequest.getDate(), appointmentRequest.getTime());
        return "redirect:/patients/index";
    }
}
