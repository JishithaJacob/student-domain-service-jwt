package io.project.studentdomainservice.service;

import io.project.studentdomainservice.model.SecurityStudent;
import io.project.studentdomainservice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaStudentDetailsService implements UserDetailsService {

    @Autowired
    StudentRepository studentRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return studentRepository.findByUsername(username).map(SecurityStudent::new)
                .orElseThrow(()->new UsernameNotFoundException("Username not found"+ username));
    }
}

