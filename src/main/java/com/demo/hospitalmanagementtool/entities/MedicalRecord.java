package com.demo.hospitalmanagementtool.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Column(name = "date_of_visit")
    private LocalDate dateOfVisit;

    private String diagnosis;

    @Column(name = "treatment_plan")
    private String treatmentPlan;

    @Column(name = "medications_prescribed")
    private String medicationsPrescribed;

    @Column(name = "lab_results")
    private String labResults;

    @Column(name = "imaging_results")
    private String imagingResults;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

}

