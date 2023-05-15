package com.demo.hospitalmanagementtool.security.token.services.impl;

import com.demo.hospitalmanagementtool.security.models.User;
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

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
