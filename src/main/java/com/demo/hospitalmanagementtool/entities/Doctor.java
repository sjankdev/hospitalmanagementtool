package com.demo.hospitalmanagementtool.entities;

import com.demo.hospitalmanagementtool.security.models.Role;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "doctor")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id", nullable = false, updatable = false)
    private Long id;

    @Size(min = 3, max = 20)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Size(min = 6)
    private String password;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String specialty;

    @Column(name = "medical_license_number", nullable = false)
    private String medicalLicenseNumber;

    @Column(name = "medical_school_attended", nullable = false)
    private String medicalSchoolAttended;

    @OneToMany(mappedBy = "doctor")
    private List<MedicalRecord> medicalRecords;

    @OneToMany(mappedBy = "doctor")
    private List<Patient> patients;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "doctor")
    private List<Billing> bills;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "doctor_roles",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Doctor(String username, String firstName, String lastName, String email, String password, LocalDate dateOfBirth, String gender, String address, String phoneNumber, String specialty, String medicalLicenseNumber, String medicalSchoolAttended) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.specialty = specialty;
        this.medicalLicenseNumber = medicalLicenseNumber;
        this.medicalSchoolAttended = medicalSchoolAttended;
    }
}