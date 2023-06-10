package com.demo.hospitalmanagementtool.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointment_request")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppointmentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(nullable = false)
    private LocalDateTime dateTime;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AppointmentRequestApprovalStatus appointmentRequestApprovalStatus;

    public AppointmentRequest(Patient patient, Doctor doctor, LocalDate dateTime) {
        this.patient = patient;
        this.doctor = doctor;
        this.dateTime = dateTime.atStartOfDay();
    }
}
