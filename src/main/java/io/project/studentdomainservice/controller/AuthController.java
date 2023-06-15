package io.project.studentdomainservice.controller;

import io.project.studentdomainservice.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    TokenService tokenService;

    @PostMapping("/token")
    public  String token(Authentication authentication){
        return tokenService.generateToken(authentication);
    }

}
