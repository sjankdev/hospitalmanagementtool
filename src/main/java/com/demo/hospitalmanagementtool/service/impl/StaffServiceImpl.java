package com.demo.hospitalmanagementtool.service.impl;

import com.demo.hospitalmanagementtool.entities.Staff;
import com.demo.hospitalmanagementtool.exceptions.NotFoundException;
import com.demo.hospitalmanagementtool.repository.StaffRepository;
import com.demo.hospitalmanagementtool.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    StaffRepository staffRepository;

    @Override
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    @Override
    public Staff getStaffById(Long id) {
        return staffRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Staff with ID " + id + " not found."));
    }

    @Override
    public void saveStaff(Staff staff) {
        staffRepository.save(staff);
    }

    @Override
    public void updateStaff(Long id, Staff staff) {
        Staff existingStaff = getStaffById(id);
        existingStaff.setFirstName(staff.getFirstName());
        existingStaff.setLastName(staff.getLastName());
        existingStaff.setDateOfBirth(staff.getDateOfBirth());
        existingStaff.setGender(staff.getGender());
        existingStaff.setAddress(staff.getAddress());
        existingStaff.setPhoneNumber(staff.getPhoneNumber());
        existingStaff.setEmail(staff.getEmail());
        existingStaff.setJobTitle(staff.getJobTitle());
        staffRepository.save(existingStaff);
    }

    @Override
    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }
}

