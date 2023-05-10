package com.demo.hospitalmanagementtool.controllers;

import com.demo.hospitalmanagementtool.entities.Patient;
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

    @GetMapping("/list")
    public String getAllPatients(Model model) {
        List<Patient> patients = patientService.getAllPatients();
        model.addAttribute("patients", patients);
        return "patient/list";
    }

    @GetMapping("/{id}")
    public String getPatientById(@PathVariable Long id, Model model) {
        Patient patient = patientService.getPatientById(id);
        model.addAttribute("patient", patient);
        return "patient/details";
      }

    @GetMapping("/create")
    public String newPatient(Model model) {
        model.addAttribute("patient", new Patient());
        return "patient/create";
    }

    @PostMapping("/save")
    public String savePatient(@ModelAttribute("patient") Patient patient) {
        patientService.savePatient(patient);
        return "redirect:/patients/list";
    }

    @GetMapping("/{id}/edit")
    public String editPatient(@PathVariable Long id, Model model) {
        Patient patient = patientService.getPatientById(id);
        model.addAttribute("patient", patient);
        return "patient/edit";
    }

    @PostMapping("/{id}/update")
    public String updatePatient(@PathVariable Long id, @ModelAttribute("patient") Patient patient) {
        patientService.updatePatient(id, patient);
        return "redirect:/patients/list";
    }

    @GetMapping("/{id}/delete")
    public String deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return "redirect:/patients/list";
    }

}
