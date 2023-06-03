package com.demo.hospitalmanagementtool.security.payload.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientLoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
