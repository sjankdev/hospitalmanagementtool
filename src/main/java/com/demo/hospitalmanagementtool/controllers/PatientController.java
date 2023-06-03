package com.demo.hospitalmanagementtool.controllers;

import com.demo.hospitalmanagementtool.entities.Appointment;
import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.entities.Patient;
import com.demo.hospitalmanagementtool.exceptions.NotFoundException;
import com.demo.hospitalmanagementtool.repository.DoctorRepository;
import com.demo.hospitalmanagementtool.service.DoctorService;
import com.demo.hospitalmanagementtool.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    DoctorRepository doctorRepository;

    @GetMapping("/list")
    public String getAllPatients(Model model) {
        List<Patient> patients = patientService.getAllPatients();
        model.addAttribute("patients", patients);
        return "patient/list";
    }

    @GetMapping("/{id}/details")
    public String getPatientById(@PathVariable Long id, Model model) {
        Patient patient = patientService.getPatientById(id);
        model.addAttribute("patient", patient);
        return "patient/details";
    }

    @GetMapping("/create")
    public String newPatient(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("doctors", doctorService.getAllDoctors());

        return "patient/create";
    }

    @PostMapping("/save")
    public String savePatient(@ModelAttribute("patient") Patient patient) {
        patientService.savePatient(patient);
        return "redirect:/patients/list";
    }

    @GetMapping("/{id}/edit")
    public String editPatient(@PathVariable Long id, Model model) {
        try {
            Patient patient = patientService.getPatientById(id);
            model.addAttribute("patient", patient);
            model.addAttribute("doctors", doctorService.getAllDoctors());
            return "patient/edit";
        } catch (NotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }

    }

    @PostMapping("/{id}/update")
    public String updatePatient(@PathVariable Long id, @ModelAttribute("patient") Patient patient) {
        patientService.updatePatient(id, patient);
        return "redirect:/patients/list";
    }

    @PostMapping("/{id}/delete")
    public String deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return "redirect:/patients/list";
    }

    @GetMapping("/{patientId}/select-doctor")
    public String showDoctorSelectionForm(@PathVariable Long patientId, Model model) {
        Patient patient = patientService.getPatientById(patientId);
        List<Doctor> availableDoctors = doctorRepository.findAll();

        model.addAttribute("patient", patient);
        model.addAttribute("doctors", availableDoctors);

        return "patient/index";
    }

    @PostMapping("/{patientId}/save-select-doctor")
    public String processDoctorSelection(@PathVariable Long patientId, @RequestParam("doctorId") Long doctorId) {
        patientService.assignDoctorToPatient(patientId, doctorId);

        return "redirect:/patients/{patientId}/details";
    }
}
