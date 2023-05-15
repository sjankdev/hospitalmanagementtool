package com.demo.hospitalmanagementtool.service;

import com.demo.hospitalmanagementtool.entities.Inventory;

import java.util.List;

public interface InventoryService {

    List<Inventory> getAllInventory();

    Inventory getInventoryById(Long id);

    void saveInventory(Inventory inventory);

    void updateInventory(Long id, Inventory inventory);

    void deleteInventory(Long id);

}
