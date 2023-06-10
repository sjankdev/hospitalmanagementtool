package com.demo.hospitalmanagementtool.security.token.services.impl;

import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.security.models.ERole;
import com.demo.hospitalmanagementtool.security.models.Role;
import com.demo.hospitalmanagementtool.security.models.User;
import com.demo.hospitalmanagementtool.security.repository.RoleRepository;
import com.demo.hospitalmanagementtool.security.repository.UserRepository;
import com.demo.hospitalmanagementtool.security.token.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void assignUserRole(User user) {
        Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("User role not found"));
        user.getRoles().add(userRole);
    }

}
