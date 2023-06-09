package com.demo.hospitalmanagementtool.security.controllers;

import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.repository.DoctorRepository;
import com.demo.hospitalmanagementtool.security.payload.request.DoctorLoginRequest;
import com.demo.hospitalmanagementtool.security.payload.request.DoctorSignupRequest;
import com.demo.hospitalmanagementtool.security.token.jwt.JwtUtils;
import com.demo.hospitalmanagementtool.security.token.services.UserDetailsImpl;
import com.demo.hospitalmanagementtool.service.DoctorService;
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
@RequestMapping("/api/auth/doctor")
public class DoctorAuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    DoctorService doctorService;


    @PostMapping("/login")
    @Transactional
    public String login(@Valid @ModelAttribute("login") DoctorLoginRequest doctorLoginRequest, BindingResult result, HttpServletResponse response, Model model) {

        Optional<Doctor> doctor = doctorRepository.findByUsername(doctorLoginRequest.getUsername());

        if (doctor.isEmpty()) {
            doctor = Optional.of(new Doctor());
            result.rejectValue("username", "error.adminUserModel", "Username doesn't exist.");
        }
        if (!encoder.matches(doctorLoginRequest.getPassword(), doctor.get().getPassword())) {
            result.rejectValue("password", "error.adminUserModel", "Wrong password, try again.");
        }
        if (result.hasErrors()) {
            return "security/login_form_doctor";
        } else {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(doctorLoginRequest.getUsername(), doctorLoginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user);
            response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
        }

        model.addAttribute("login", doctorLoginRequest);


        return "redirect:/doctor/index";
    }


    @PostMapping("/signup")
    @Transactional
    public String signup(@Valid @ModelAttribute("signup") DoctorSignupRequest doctorSignupRequest, BindingResult result, Model model) throws Exception {

        boolean thereAreErrors = result.hasErrors();
        if (thereAreErrors) {
            model.addAttribute("signup", doctorSignupRequest);
            return "security/register_form_doctor";
        }

        Optional<Doctor> doctorUsername = doctorRepository.findByUsername(doctorSignupRequest.getUsername());
        Boolean doctorEmail = doctorRepository.existsByEmail(doctorSignupRequest.getEmail());

        if (doctorUsername.isPresent()) {
            doctorUsername = Optional.of(new Doctor());
            result.rejectValue("username", "error.adminUserModel", "Username already exist.");
        }
        if (doctorEmail) {
            result.rejectValue("email", "error.adminUserModel", "Email already exist.");
        }
        if (result.hasErrors()) {
            return "security/register_form_doctor";
        }
        Doctor doctor = new Doctor(doctorSignupRequest.getUsername(), doctorSignupRequest.getFirstName(), doctorSignupRequest.getLastName(), doctorSignupRequest.getEmail(), encoder.encode(doctorSignupRequest.getPassword()), doctorSignupRequest.getDateOfBirth(), doctorSignupRequest.getGender(), doctorSignupRequest.getAddress(), doctorSignupRequest.getPhoneNumber(), doctorSignupRequest.getSpecialty(), doctorSignupRequest.getMedicalLicenseNumber(), doctorSignupRequest.getMedicalSchoolAttended());
        doctorService.assignUserRole(doctor);

        model.addAttribute("signup", doctorSignupRequest);
        doctorRepository.save(doctor);

        return "redirect:/api/auth/doctor/loginForm";

    }

    @GetMapping("/registerForm")
    public String registerForm(Model model) {
        DoctorSignupRequest doctorSignupRequest = new DoctorSignupRequest();
        model.addAttribute("signup", doctorSignupRequest);
        return "security/register_form_doctor";
    }

    @GetMapping("/loginForm")
    public String loginForm(Model model) {
        DoctorLoginRequest doctorLoginRequest = new DoctorLoginRequest();
        model.addAttribute("login", doctorLoginRequest);
        return "security/login_form_doctor";
    }

}