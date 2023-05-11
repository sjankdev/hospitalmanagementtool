package com.demo.hospitalmanagementtool.controllers;

import com.demo.hospitalmanagementtool.entities.Billing;
import com.demo.hospitalmanagementtool.service.AppointmentService;
import com.demo.hospitalmanagementtool.service.BillingService;
import com.demo.hospitalmanagementtool.service.DoctorService;
import com.demo.hospitalmanagementtool.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/billing")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @GetMapping("/list")
    public String getAllBills(Model model) {
        List<Billing> bills = billingService.getAllBills();
        model.addAttribute("bills", bills);
        return "billing/list";
    }

    @GetMapping("/{id}")
    public String getBillById(@PathVariable Long id, Model model) {
        Billing bill = billingService.getBillById(id);
        model.addAttribute("bill", bill);
        return "billing/details";
    }

    @GetMapping("/create")
    public String newBill(Model model) {
        Billing bill = new Billing();
        model.addAttribute("bill", bill);
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        return "billing/create";
    }

    @PostMapping("/save")
    public String saveBill(@ModelAttribute("bill") Billing bill) {
        billingService.saveBill(bill);
        return "redirect:/billing/";
    }

    @GetMapping("/{id}/edit")
    public String editBill(@PathVariable Long id, Model model) {
        Billing bill = billingService.getBillById(id);
        model.addAttribute("bill", bill);
        return "billing/edit";
    }

    @PostMapping("/{id}/update")
    public String updateBill(@PathVariable Long id, @ModelAttribute("bill") Billing bill) {
        billingService.updateBill(id, bill);
        return "redirect:/billing/";
    }

    @GetMapping("/{id}/delete")
    public String deleteBill(@PathVariable Long id) {
        billingService.deleteBill(id);
        return "redirect:/billing/";
    }
}

