package com.demo.hospitalmanagementtool.security.payload.request;

import jakarta.validation.constraints.*;

import java.util.Set;

public class SignupRequest {
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

    private Set<String> role;

    public SignupRequest() {
    }

    public SignupRequest(String username, String firstName, String lastName, String email, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
