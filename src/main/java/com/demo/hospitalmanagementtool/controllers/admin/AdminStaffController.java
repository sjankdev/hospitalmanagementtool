package com.demo.hospitalmanagementtool.controllers.admin;

import com.demo.hospitalmanagementtool.entities.Staff;
import com.demo.hospitalmanagementtool.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/auth-staff")
public class AdminStaffController {

    @Autowired
    StaffService staffService;

    @GetMapping("/list")
    public String getAllStaff(Model model) {
        List<Staff> staffList = staffService.getAllStaff();
        model.addAttribute("staffList", staffList);
        return "staff/list";
    }

    @GetMapping("/{id}/details")
    public String getStaffById(@PathVariable Long id, Model model) {
        Staff staff = staffService.getStaffById(id);
        model.addAttribute("staff", staff);
        return "staff/details";
    }

    @GetMapping("/create")
    public String newStaff(Model model) {
        model.addAttribute("staff", new Staff());
        return "staff/create";
    }

    @PostMapping("/save")
    public String saveStaff(@ModelAttribute("staff") Staff staff) {
        staffService.saveStaff(staff);
        return "redirect:/auth-staff/list";
    }

    @GetMapping("/{id}/edit")
    public String editStaff(@PathVariable Long id, Model model) {
        Staff staff = staffService.getStaffById(id);
        model.addAttribute("staff", staff);
        return "staff/edit";
    }

    @PostMapping("/{id}/update")
    public String updateStaff(@PathVariable Long id, @ModelAttribute("staff") Staff staff) {
        staffService.updateStaff(id, staff);
        return "redirect:/auth-staff/list";
    }

    @PostMapping("/{id}/delete")
    public String deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return "redirect:/auth-staff/list";
    }
}