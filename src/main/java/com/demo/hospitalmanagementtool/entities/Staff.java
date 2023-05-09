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
public class Staff {

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

    @Column(name = "job_title")
    private String jobTitle;

    @OneToMany(mappedBy = "doctor")
    private List<MedicalRecord> medicalRecords;

    @OneToMany(mappedBy = "staff")
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "doctor")
    private List<Billing> bills;


}

