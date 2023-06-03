package com.demo.hospitalmanagementtool.security.controllers;

import com.demo.hospitalmanagementtool.entities.Patient;
import com.demo.hospitalmanagementtool.repository.PatientRepository;
import com.demo.hospitalmanagementtool.security.models.ERole;
import com.demo.hospitalmanagementtool.security.models.Role;
import com.demo.hospitalmanagementtool.security.models.User;
import com.demo.hospitalmanagementtool.security.payload.request.LoginRequest;
import com.demo.hospitalmanagementtool.security.payload.request.PatientLoginRequest;
import com.demo.hospitalmanagementtool.security.payload.request.PatientSignupRequest;
import com.demo.hospitalmanagementtool.security.payload.request.SignupRequest;
import com.demo.hospitalmanagementtool.security.repository.RoleRepository;
import com.demo.hospitalmanagementtool.security.repository.UserRepository;
import com.demo.hospitalmanagementtool.security.token.jwt.JwtUtils;
import com.demo.hospitalmanagementtool.security.token.services.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/api/auth/patient")
public class PatientAuthController {

    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    PatientRepository patientRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    @Transactional
    public String login(@Valid @ModelAttribute("login") PatientLoginRequest patientLoginRequest, BindingResult result, HttpServletResponse response, Model model) {

        Optional<Patient> admin = patientRepository.findByUsername(patientLoginRequest.getUsername());

        if (admin.isEmpty()) {
            admin = Optional.of(new Patient());
            result.rejectValue("username", "error.adminUserModel", "Username doesn't exist.");
        }
        if (!encoder.matches(patientLoginRequest.getPassword(), admin.get().getPassword())) {
            result.rejectValue("password", "error.adminUserModel", "Wrong password, try again.");
        }

        if (result.hasErrors()) {
            return "security/login_form_patient";
        } else {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(patientLoginRequest.getUsername(), patientLoginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
            System.out.println(user.getAuthorities());
            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user);
            response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
        }

        model.addAttribute("login", patientLoginRequest);


        return "redirect:/patients/selectDoctor";
    }

    @PostMapping("/signup")
    @Transactional
    public String signup(@Valid @ModelAttribute("signup") PatientSignupRequest patientSignupRequest, BindingResult result, Model model) throws Exception {

        boolean thereAreErrors = result.hasErrors();
        if (thereAreErrors) {
            model.addAttribute("signup", patientSignupRequest);
            return "security/register_form_patient";
        }

        Optional<Patient> userUsername = patientRepository.findByUsername(patientSignupRequest.getUsername());
        Boolean userEmail = patientRepository.existsByEmail(patientSignupRequest.getEmail());

        if (userUsername.isPresent()) {
            userUsername = Optional.of(new Patient());
            result.rejectValue("username", "error.adminUserModel", "Username already exist.");
        }
        if (userEmail) {
            result.rejectValue("email", "error.adminUserModel", "Email already exist.");
        }
        if (result.hasErrors()) {
            return "security/register_form_patient";
        }
        Patient patient = new Patient(patientSignupRequest.getUsername(), patientSignupRequest.getFirstName(), patientSignupRequest.getLastName(), patientSignupRequest.getEmail(), encoder.encode(patientSignupRequest.getPassword()), patientSignupRequest.getDateOfBirth(), patientSignupRequest.getGender(), patientSignupRequest.getAddress(), patientSignupRequest.getPhoneNumber(), patientSignupRequest.getEmergencyContactName(), patientSignupRequest.getEmergencyContactPhoneNumber());
        assignUserRole(patient);

        model.addAttribute("signup", patientSignupRequest);
        patientRepository.save(patient);

        return "redirect:/api/auth/patient/loginForm";

    }

    @PostMapping("/signout")
    public String logoutUser(HttpServletResponse response) {
        ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie();
        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        return "redirect:/api/auth/loginForm";
    }

    @GetMapping("/registerForm")
    public String registerForm(Model model) {
        PatientSignupRequest patientSignupRequest = new PatientSignupRequest();
        model.addAttribute("signup", patientSignupRequest);
        return "security/register_form_patient";
    }

    @GetMapping("/loginForm")
    public String loginForm(Model model) {
        LoginRequest loginRequest = new LoginRequest();
        model.addAttribute("login", loginRequest);
        return "security/login_form_patient";
    }

    private void assignUserRole(Patient patient) {
        Role userRole = roleRepository.findByName(ERole.ROLE_PATIENT).orElseThrow(() -> new RuntimeException("User role not found"));
        patient.getRoles().add(userRole);
    }
}