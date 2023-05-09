package com.demo.hospitalmanagementtool.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private String gender;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    private String specialty;

    @Column(name = "medical_license_number")
    private String medicalLicenseNumber;

    @Column(name = "medical_school_attended")
    private String medicalSchoolAttended;

    @OneToMany(mappedBy = "doctor")
    private List<MedicalRecord> medicalRecords;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "doctor")
    private List<Billing> bills;

}
