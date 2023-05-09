package com.demo.hospitalmanagementtool.repository;

import com.demo.hospitalmanagementtool.entities.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {

}
