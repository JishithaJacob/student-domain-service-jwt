package io.project.studentdomainservice.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class SecurityStudent implements UserDetails {
    private final Student student;

    public SecurityStudent(Student student) {
        this.student = student;
    }

    @Override
    public String getUsername() {
        return student.getUserName();
    }
    @Override
    public String getPassword() {
        return student.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(student.getRole().split(","))
                .map(SimpleGrantedAuthority::new).toList();
    }

    /* .map(SimpleGrantedAuthority::new): This maps each role string to a new
    SimpleGrantedAuthority object.The SimpleGrantedAuthority class
    is typically used in Spring Security to represent a granted authority (or role).*/


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
