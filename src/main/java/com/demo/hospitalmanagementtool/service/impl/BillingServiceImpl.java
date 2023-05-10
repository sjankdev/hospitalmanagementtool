package com.demo.hospitalmanagementtool.service.impl;

import com.demo.hospitalmanagementtool.entities.Billing;
import com.demo.hospitalmanagementtool.exceptions.NotFoundException;
import com.demo.hospitalmanagementtool.repository.BillingRepository;
import com.demo.hospitalmanagementtool.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillingServiceImpl implements BillingService {

    @Autowired
    BillingRepository billingRepository;

    @Override
    public List<Billing> getAllBills() {
        return billingRepository.findAll();
    }

    @Override
    public Billing getBillById(Long id) {
        return billingRepository.findById(id).orElseThrow(() -> new NotFoundException("Billing with ID " + id + " not found."));
    }

    @Override
    public void saveBill(Billing bill) {
        billingRepository.save(bill);
    }

    @Override
    public void updateBill(Long id, Billing bill) {
        Billing existingBill = getBillById(id);
        existingBill.setDateOfBill(bill.getDateOfBill());
        existingBill.setAmount(bill.getAmount());
        existingBill.setPatient(bill.getPatient());
        existingBill.setDoctor(bill.getDoctor());
        existingBill.setAppointment(bill.getAppointment());
        billingRepository.save(existingBill);
    }

    @Override
    public void deleteBill(Long id) {
        billingRepository.deleteById(id);
    }
}

