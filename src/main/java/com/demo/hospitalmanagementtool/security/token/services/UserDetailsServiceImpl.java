package com.demo.hospitalmanagementtool.security.token.services;

import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.entities.Patient;
import com.demo.hospitalmanagementtool.repository.DoctorRepository;
import com.demo.hospitalmanagementtool.repository.PatientRepository;
import com.demo.hospitalmanagementtool.security.models.User;
import com.demo.hospitalmanagementtool.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return UserDetailsImpl.build(user);
        }

        Optional<Patient> patientOptional = patientRepository.findByUsername(username);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            return UserDetailsImpl.buildFromPatient(patient);
        }

        Optional<Doctor> doctorOptional = doctorRepository.findByUsername(username);
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            return UserDetailsImpl.buildFromDoctor(doctor);
        }

        throw new UsernameNotFoundException("User or Patient Not Found with username: " + username);
    }


}
