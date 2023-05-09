package com.demo.hospitalmanagementtool.repository;

import com.demo.hospitalmanagementtool.entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

}
