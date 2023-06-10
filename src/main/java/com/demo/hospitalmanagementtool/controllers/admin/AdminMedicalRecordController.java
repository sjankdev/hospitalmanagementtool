package com.demo.hospitalmanagementtool.controllers.admin;

import com.demo.hospitalmanagementtool.entities.MedicalRecord;
import com.demo.hospitalmanagementtool.service.AppointmentService;
import com.demo.hospitalmanagementtool.service.DoctorService;
import com.demo.hospitalmanagementtool.service.MedicalRecordService;
import com.demo.hospitalmanagementtool.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/auth-medicalrecords")
public class AdminMedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/list")
    public String getAllMedicalRecords(Model model) {
        List<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords();
        model.addAttribute("medicalRecords", medicalRecords);
        return "medicalRecord/list";
    }

    @GetMapping("/{id}/details")
    public String getMedicalRecordById(@PathVariable Long id, Model model) {
        MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordById(id);
        model.addAttribute("medicalRecord", medicalRecord);
        return "medicalRecord/details";
    }

    @GetMapping("/create")
    public String newMedicalRecord(Model model) {
        model.addAttribute("medicalRecord", new MedicalRecord());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        return "medicalRecord/create";
    }

    @PostMapping("/save")
    public String saveMedicalRecord(@ModelAttribute("medicalRecord") MedicalRecord medicalRecord) {
        medicalRecordService.saveMedicalRecord(medicalRecord);
        return "redirect:/auth-medicalrecords/list";
    }

    @GetMapping("/{id}/edit")
    public String editMedicalRecord(@PathVariable Long id, Model model) {
        MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordById(id);
        model.addAttribute("medicalRecord", medicalRecord);
        return "medicalRecord/edit";
    }

    @PostMapping("/{id}/update")
    public String updateMedicalRecord(@PathVariable Long id, @ModelAttribute("medicalRecord") MedicalRecord medicalRecord) {
        medicalRecordService.updateMedicalRecord(id, medicalRecord);
        return "redirect:/auth-medicalrecords/list";
    }

    @PostMapping("/{id}/delete")
    public String deleteMedicalRecord(@PathVariable Long id) {
        medicalRecordService.deleteMedicalRecord(id);
        return "redirect:/auth-medicalrecords/list";
    }
}