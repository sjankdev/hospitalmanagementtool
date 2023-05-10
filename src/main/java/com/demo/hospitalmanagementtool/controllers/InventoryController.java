package com.demo.hospitalmanagementtool.controllers;

import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.entities.Inventory;
import com.demo.hospitalmanagementtool.service.DoctorService;
import com.demo.hospitalmanagementtool.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/list")
    public String getAllInventory(Model model) {
        List<Inventory> inventoryList = inventoryService.getAllInventory();
        model.addAttribute("inventoryList", inventoryList);
        return "inventory/list";
    }

    @GetMapping("/{id}")
    public String getInventoryById(@PathVariable Long id, Model model) {
        Inventory inventory = inventoryService.getInventoryById(id);
        model.addAttribute("inventory", inventory);
        return "inventory/details";
    }

    @GetMapping("/create")
    public String newInventory(Model model) {
        model.addAttribute("inventory", new Inventory());
        return "inventory/create";
    }

    @PostMapping("/save")
    public String saveInventory(@ModelAttribute("inventory") Inventory inventory) {
        inventoryService.saveInventory(inventory);
        return "redirect:/inventory/";
    }

    @GetMapping("/{id}/edit")
    public String editInventory(@PathVariable Long id, Model model) {
        Inventory inventory = inventoryService.getInventoryById(id);
        model.addAttribute("inventory", inventory);
        return "inventory/edit";
    }

    @PostMapping("/{id}/update")
    public String updateInventory(@PathVariable Long id, @ModelAttribute("inventory") Inventory inventory) {
        inventoryService.updateInventory(id, inventory);
        return "redirect:/inventory/";
    }

    @GetMapping("/{id}/delete")
    public String deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return "redirect:/inventory/";
    }
}
