package com.demo.hospitalmanagementtool.security.token;

import com.demo.hospitalmanagementtool.security.token.jwt.AuthTokenFilter;
import com.demo.hospitalmanagementtool.security.token.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true)
public class WebSecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .requestMatchers("/auth-appointments/list", "/auth-appointments/{id}/details").hasAnyRole("ADMIN", "MODERATOR")
                .requestMatchers("/auth-appointments/**").hasRole("ADMIN")
                .requestMatchers("/auth-billing/list", "/auth-billing/{id}/details").hasAnyRole("ADMIN", "MODERATOR")
                .requestMatchers("/auth-billing/**").hasRole("ADMIN")
                .requestMatchers("/auth-doctors/list", "/auth-doctors/{id}/details").hasAnyRole("ADMIN", "MODERATOR")
                .requestMatchers("/auth-doctors/**").hasRole("ADMIN")
                .requestMatchers("/auth-inventory/list", "/auth-inventory/{id}/details").hasAnyRole("ADMIN", "MODERATOR")
                .requestMatchers("/auth-inventory/**").hasRole("ADMIN")
                .requestMatchers("/auth-medicalrecords/list", "/auth-medicalrecords/{id}/details").hasAnyRole("ADMIN", "MODERATOR")
                .requestMatchers("/auth-medicalrecords/**").hasRole("ADMIN")
                .requestMatchers("/auth-patients/list", "/auth-patients/{id}/details").hasAnyRole("ADMIN", "MODERATOR")
                .requestMatchers("/auth-patients/**").hasRole("ADMIN")
                .requestMatchers("/auth-staff/list", "/auth-staff/{id}/details").hasAnyRole("ADMIN", "MODERATOR")
                .requestMatchers("/auth-staff/**").hasRole("ADMIN")
                .requestMatchers("/doctor/**").hasRole("DOCTOR")
                .requestMatchers("/patient/**").hasRole("PATIENT")
                .anyRequest().permitAll()
                .and()
                .formLogin().loginPage("/api/auth/loginForm")
                .permitAll();

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
