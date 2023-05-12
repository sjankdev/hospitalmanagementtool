package com.demo.hospitalmanagementtool.service;

import com.demo.hospitalmanagementtool.entities.Staff;

import java.util.List;

public interface StaffService {

    List<Staff> getAllStaff();

    Staff getStaffById(Long id);

    void saveStaff(Staff staff);

    void updateStaff(Long id, Staff staff);

    void deleteStaff(Long id);

}
