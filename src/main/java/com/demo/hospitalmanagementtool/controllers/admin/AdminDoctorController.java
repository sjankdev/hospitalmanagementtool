package com.demo.hospitalmanagementtool.controllers.admin;

import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/auth-doctors")
public class AdminDoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/list")
    public String getAllDoctors(Model model) {
        List<Doctor> doctors = doctorService.getAllDoctors();
        model.addAttribute("doctors", doctors);
        return "admin/doctor/list";
    }

    @GetMapping("/{id}/details")
    public String getDoctorById(@PathVariable Long id, Model model) {
        model.addAttribute("doctor", doctorService.getDoctorById(id));
        return "admin/doctor/details";
    }

    @GetMapping("/create")
    public String newDoctor(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "admin/doctor/create";
    }

    @PostMapping("/save")
    public String addDoctor(@Valid @ModelAttribute("doctor") Doctor doctor, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/doctor/create";
        }
        doctorService.saveDoctor(doctor);
        return "redirect:/auth-doctors/list";
    }

    @GetMapping("/{id}/edit")
    public String editDoctor(@PathVariable Long id, Model model) {
        model.addAttribute("doctor", doctorService.getDoctorById(id));
        return "admin/doctor/edit";
    }

    @PostMapping("/{id}/update")
    public String updateDoctor(@PathVariable Long id, @Valid @ModelAttribute("doctor") Doctor doctor, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/doctor/edit";
        }
        doctorService.updateDoctor(id, doctor);
        return "redirect:/auth-doctors/list";
    }

    @PostMapping("/{id}/delete")
    public String deleteDoctor(@PathVariable Long id, @RequestParam("_method") String method) {
        if (method.equalsIgnoreCase("delete")) {
            doctorService.deleteDoctor(id);
        }
        return "redirect:/auth-doctors/list";
    }
}