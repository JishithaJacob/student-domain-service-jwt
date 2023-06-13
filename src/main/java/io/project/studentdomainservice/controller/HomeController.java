package io.project.studentdomainservice.controller;

//import io.project.studentdomainservice.model.Student;
//import io.project.studentdomainservice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class HomeController {

   /* @Autowired
    StudentRepository repository;
    @Autowired
    PasswordEncoder encoder;*/
    @GetMapping("/")
    public String home(Principal principal) {
        return "Hello, " + principal.getName();
    }

    /*@PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String user() {
        return "Hello, User!";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String admin() {
        return "Hello, Admin!";
    }

    @GetMapping("/newadmin")
    public String newAdmin() {
        return "Hello,NEW Admin!";
    }

    @PostMapping("/createStudent")
    public Student createStudent(@RequestBody Map<String,String> body){
        String name= body.get("name");
        String password = encoder.encode(body.get("password"));
        String role = body.get("role");
        int enabled = Integer.parseInt(body.get("enabled"));
        return repository.save(new Student(name,password,role,enabled));
    }
*/
}
