package com.demo.hospitalmanagementtool.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
        this.id = appointmentRequest.getId();
        this.patient = appointmentRequest.getPatient();
        this.doctor = appointmentRequest.getDoctor();
        this.dateTime = appointmentRequest.getDateTime();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "date_time", nullable = false)
    @Future(message = "Date and time must be in the future")
    @NotNull
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @NotNull
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @NotNull
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @Column(name = "reason_for_visit", nullable = false)
    @NotBlank
    @Pattern(regexp = "^(?!\\d+$).+", message = "Reason for visit is not valid, can not contain just number")
    private String reasonForVisit;

    @Column(nullable = false)
    @NotBlank
    @Pattern(regexp = "^(?!\\d+$).+", message = "Notes is not valid, can not contain just number")
    private String notes;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private AppointmentStatus status;

    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
    private MedicalRecord medicalRecord;

    public Appointment(LocalDateTime dateTime, Patient patient, Doctor doctor) {
        this.dateTime = dateTime;
        this.patient = patient;
        this.doctor = doctor;
    }

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