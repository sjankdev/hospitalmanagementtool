package com.demo.hospitalmanagementtool.controllers;


import com.demo.hospitalmanagementtool.entities.AppointmentRequest;
import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.entities.Patient;
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
        Patient patient = patientService.getPatientById(patientId);
        Doctor doctor = patient.getDoctor(); // Fetch the connected doctor

        model.addAttribute("patient", patient);
        model.addAttribute("doctor", doctor);
        model.addAttribute("appointmentRequest", new AppointmentRequest());
        return "patient/create-appointment-request";
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


}
