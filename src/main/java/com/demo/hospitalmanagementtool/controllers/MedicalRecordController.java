package com.demo.hospitalmanagementtool.controllers;

import com.demo.hospitalmanagementtool.entities.MedicalRecord;
import com.demo.hospitalmanagementtool.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
    @RequestMapping("/medicalrecords")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping("/list")
    public String getAllMedicalRecords(Model model) {
        List<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords();
        model.addAttribute("medicalRecords", medicalRecords);
        return "medicalrecord/list";
    }

    @GetMapping("/{id}")
    public String getMedicalRecordById(@PathVariable Long id, Model model) {
        MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordById(id);
        model.addAttribute("medicalRecord", medicalRecord);
        return "medicalrecord/details";
    }

    @GetMapping("/create")
    public String newMedicalRecord(Model model) {
        model.addAttribute("medicalRecord", new MedicalRecord());
        return "medicalrecord/create";
    }

    @PostMapping("/save")
    public String saveMedicalRecord(@ModelAttribute("medicalRecord") MedicalRecord medicalRecord) {
        medicalRecordService.saveMedicalRecord(medicalRecord);
        return "redirect:/medicalrecords/";
    }

    @GetMapping("/{id}/edit")
    public String editMedicalRecord(@PathVariable Long id, Model model) {
        MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordById(id);
        model.addAttribute("medicalRecord", medicalRecord);
        return "medicalrecord/edit";
    }

    @PostMapping("/{id}/update")
    public String updateMedicalRecord(@PathVariable Long id, @ModelAttribute("medicalRecord") MedicalRecord medicalRecord) {
        medicalRecordService.updateMedicalRecord(id, medicalRecord);
        return "redirect:/medicalrecords/";
    }

    @GetMapping("/{id}/delete")
    public String deleteMedicalRecord(@PathVariable Long id) {
        medicalRecordService.deleteMedicalRecord(id);
        return "redirect:/medicalrecords/";
    }

}
