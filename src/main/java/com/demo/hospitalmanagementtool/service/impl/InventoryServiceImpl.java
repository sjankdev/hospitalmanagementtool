package com.demo.hospitalmanagementtool.service.impl;

import com.demo.hospitalmanagementtool.entities.Inventory;
import com.demo.hospitalmanagementtool.exceptions.NotFoundException;
import com.demo.hospitalmanagementtool.repository.InventoryRepository;
import com.demo.hospitalmanagementtool.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    @Override
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    @Override
    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Inventory with ID " + id + " not found."));
    }

    @Override
    public void saveInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
    }

    @Override
    public void updateInventory(Long id, Inventory inventory) {
        Inventory existingInventory = getInventoryById(id);
        existingInventory.setName(inventory.getName());
        existingInventory.setDescription(inventory.getDescription());
        existingInventory.setQuantity(inventory.getQuantity());
        existingInventory.setPrice(inventory.getPrice());
        inventoryRepository.save(existingInventory);
    }

    @Override
    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }
}

