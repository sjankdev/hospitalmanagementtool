package com.demo.hospitalmanagementtool.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "billing")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Positive(message = "Amount must be a positive value.")
    private double amount;

    @NotBlank(message = "Status is required.")
    private String status;

    @PastOrPresent(message = "Date of bill must be in the past or present.")
    @Column(name = "date_of_bill")
    private LocalDateTime dateOfBill;


}
