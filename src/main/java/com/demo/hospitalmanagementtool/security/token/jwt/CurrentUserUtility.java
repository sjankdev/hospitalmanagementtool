package com.demo.hospitalmanagementtool.security.token.jwt;

import com.demo.hospitalmanagementtool.security.token.services.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("CurrentUserUtility")
public class CurrentUserUtility {

    public static UserDetailsImpl getCurrentUser() {
        Authentication tAuthentication = getAuthentication();
        if (tAuthentication != null) {
            return (UserDetailsImpl) getAuthentication().getPrincipal();

        } else {

            return null;
        }
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
