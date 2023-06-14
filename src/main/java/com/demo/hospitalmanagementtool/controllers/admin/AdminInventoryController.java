package com.demo.hospitalmanagementtool.controllers.admin;

import com.demo.hospitalmanagementtool.entities.Inventory;
import com.demo.hospitalmanagementtool.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/auth-inventory")
public class AdminInventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/list")
    public String getAllInventory(Model model) {
        List<Inventory> inventoryList = inventoryService.getAllInventory();
        model.addAttribute("inventoryList", inventoryList);
        return "admin/inventory/list";
    }

    @GetMapping("/{id}/details")
    public String getInventoryById(@PathVariable Long id, Model model) {
        Inventory inventory = inventoryService.getInventoryById(id);
        model.addAttribute("inventory", inventory);
        return "admin/inventory/details";
    }

    @GetMapping("/create")
    public String newInventory(Model model) {
        model.addAttribute("inventory", new Inventory());
        return "admin/inventory/create";
    }

    @PostMapping("/save")
    public String saveInventory(@ModelAttribute("inventory") Inventory inventory) {
        inventoryService.saveInventory(inventory);
        return "redirect:/auth-inventory/list";
    }

    @GetMapping("/{id}/edit")
    public String editInventory(@PathVariable Long id, Model model) {
        model.addAttribute("inventory", inventoryService.getInventoryById(id));
        return "admin/inventory/edit";
    }

    @PostMapping("/{id}/update")
    public String updateInventory(@PathVariable Long id, @Valid @ModelAttribute("inventory") Inventory inventory, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/inventory/edit";
        }
        inventoryService.updateInventory(id, inventory);
        return "redirect:/auth-inventory/list";
    }

    @PostMapping("/{id}/delete")
    public String deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return "redirect:/auth-inventory/list";
    }
}