package com.demo.hospitalmanagementtool.service;

import com.demo.hospitalmanagementtool.entities.Billing;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface BillingService {

    List<Billing> getAllBills();

    Billing getBillById(Long id);

    void saveBill(Billing billing);

    void updateBill(Long id, Billing billing);

    void deleteBill(Long id);

}
