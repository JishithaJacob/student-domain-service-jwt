package io.project.studentdomainservice.config;

import io.project.studentdomainservice.service.JpaStudentDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    JpaStudentDetailsService jpaStudentDetailsService;
    @Bean
    /*WebSecurityConfigureAdapter got deprecated therefore using SecurityFilterChain*/
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
           return http
                   .csrf(csrf -> csrf.ignoringAntMatchers("/createStudent"))
                   .authorizeRequests(auth->auth
                           .antMatchers("/createStudent").permitAll()
                           .mvcMatchers("/api/students").permitAll()
                           .mvcMatchers("/newadmin").hasAnyRole("ADMIN")
                           .anyRequest().authenticated())
                   .userDetailsService(jpaStudentDetailsService)
                   .headers(headers -> headers.frameOptions().sameOrigin())
                   .httpBasic(withDefaults())
                   .build();

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
