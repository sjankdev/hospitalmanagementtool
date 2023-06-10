package com.demo.hospitalmanagementtool.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Appointment {

    public Appointment(AppointmentRequest appointmentRequest) {
        // Convert relevant attributes from AppointmentRequest to Appointment
        this.id = appointmentRequest.getId();
        this.patient = appointmentRequest.getPatient();
        this.doctor = appointmentRequest.getDoctor();
        this.dateTime = appointmentRequest.getDateTime();
        // Set other attributes as needed
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @Column(name = "reason_for_visit", nullable = false)
    private String reasonForVisit;

    @Column(nullable = false)
    private String notes;

    @Column(nullable = false)
    private String status;

    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
    private MedicalRecord medicalRecord;

    public void setStaff(Staff staff) {
        if (this.staff != null) {
            this.staff.getAppointments().remove(this);
        }

        this.staff = staff;

        if (staff != null) {
            staff.getAppointments().add(this);
        }
    }
}