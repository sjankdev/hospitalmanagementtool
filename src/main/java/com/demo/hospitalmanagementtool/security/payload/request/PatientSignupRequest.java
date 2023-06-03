package com.demo.hospitalmanagementtool.security.payload.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientSignupRequest {

    @NotBlank(message = "Please insert username")
    @Size(min = 3, max = 20, message = "Username must have at least 3 characters but no more than 20")
    private String username;

    @NotBlank(message = "Please insert first name")
    private String firstName;

    @NotBlank(message = "Please insert last name")
    private String lastName;

    @NotBlank(message = "Please insert email")
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank(message = "Please insert password")
    @Size(min = 6, max = 40, message = "Password must have at least 6 characters but no more than 40")
    private String password;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "emergency_contact_name", nullable = false)
    private String emergencyContactName;

    @Column(name = "emergency_contact_phone_number", nullable = false)
    private String emergencyContactPhoneNumber;

    private Set<String> role;


}
