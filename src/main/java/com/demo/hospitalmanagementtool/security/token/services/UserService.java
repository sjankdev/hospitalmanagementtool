package com.demo.hospitalmanagementtool.security.token.services;


import com.demo.hospitalmanagementtool.security.models.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);

}
