package com.demo.hospitalmanagementtool.repository;

import com.demo.hospitalmanagementtool.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

}
