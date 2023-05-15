package com.demo.hospitalmanagementtool.security.token;

import com.demo.hospitalmanagementtool.security.token.jwt.AuthTokenFilter;
import com.demo.hospitalmanagementtool.security.token.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
//@EnableWebSecurity
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
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
                .requestMatchers("/appointments/list", "/appointments/{id}/details").hasAnyRole("MODERATOR", "ADMIN")
                .requestMatchers("/appointments/**").hasRole("ADMIN")
                .requestMatchers("/billing/list", "/billing/{id}/details").hasAnyRole("MODERATOR", "ADMIN")
                .requestMatchers("/billing/**").hasRole("ADMIN")
                .requestMatchers("/doctors/list", "/doctors/{id}/details").hasAnyRole("MODERATOR", "ADMIN")
                .requestMatchers("/doctors/**").hasRole("ADMIN")
                .requestMatchers("/inventory/list", "/inventory/{id}/details").hasAnyRole("MODERATOR", "ADMIN")
                .requestMatchers("/inventory/**").hasRole("ADMIN")
                .requestMatchers("/medicalrecords/list", "/medicalrecords/{id}/details").hasAnyRole("MODERATOR", "ADMIN")
                .requestMatchers("/medicalrecords/**").hasRole("ADMIN")
                .requestMatchers("/patients/list", "/patients/{id}/details").hasAnyRole("MODERATOR", "ADMIN")
                .requestMatchers("/patients/**").hasRole("ADMIN")
                .requestMatchers("/staff/list", "/staff/{id}/details").hasAnyRole("MODERATOR", "ADMIN")
                .requestMatchers("/staff/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin().loginPage("/api/auth/loginForm")
                .permitAll();

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
