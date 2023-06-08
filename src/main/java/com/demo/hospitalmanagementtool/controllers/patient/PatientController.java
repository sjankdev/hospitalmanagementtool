package com.demo.hospitalmanagementtool.controllers.patient;


import com.demo.hospitalmanagementtool.entities.AppointmentRequest;
import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.entities.Patient;
import com.demo.hospitalmanagementtool.repository.DoctorRepository;
import com.demo.hospitalmanagementtool.service.AppointmentRequestService;
import com.demo.hospitalmanagementtool.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentRequestService appointmentRequestService;

    @Autowired
    DoctorRepository doctorRepository;

    @GetMapping("/index")
    public String index(Model model, Principal principal) {
        String username = principal.getName();

        Optional<Patient> optionalPatient = patientService.getPatientByUsername(username);

        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            model.addAttribute("patient", patient);
            return "patient/index";
        } else {
            return "error";
        }
    }

    @GetMapping("/{patientId}/select-doctor")
    public String showDoctorSelectionForm(@PathVariable Long patientId, Model model) {
        Patient patient = patientService.getPatientById(patientId);
        List<Doctor> availableDoctors = doctorRepository.findAll();

        model.addAttribute("patient", patient);
        model.addAttribute("doctors", availableDoctors);

        return "patient/firstLogin";
    }

    @PostMapping("/{patientId}/save-select-doctor")
    public String processDoctorSelection(@PathVariable Long patientId, @RequestParam("doctorId") Long doctorId) {
        patientService.assignDoctorToPatient(patientId, doctorId);
        return "redirect:/patients/index";
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
        return "redirect:/patient/index";
    }

}
