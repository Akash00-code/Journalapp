package net.engineeringdigest.journalapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalapp.Entity.User;
import net.engineeringdigest.journalapp.Service.JwtService;
import net.engineeringdigest.journalapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {
    private  final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public PublicController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @GetMapping("/health-check")
    public String Health(@RequestParam String username) {
        return STR."Your health is good \{username} keep doing exercise regularly!";
    }
    @GetMapping("/csrf")
    public CsrfToken getToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }
    @Autowired
    private UserService userService;
    @Autowired
    PasswordEncoder encoder;
    @PostMapping("/SignUp")
    public ResponseEntity<?> CreateUser(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        userService.SaveUser(user);
        return new  ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping("/forgot-password")
    public ResponseEntity<?> ForgotPassword(@RequestBody User Newuser) {
        User user=userService.findByUserName(Newuser.getUserName());
        if(user==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setPassword(encoder.encode(Newuser.getPassword()));
        userService.SaveUser(user);
        return new ResponseEntity<>("password changed",HttpStatus.OK);
    }
    @PostMapping("/Login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        try{
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            String jwt = jwtService.generateToken(authenticate.getName());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch(Exception e){
            log.error("Authentication failed.");
            return new ResponseEntity<>(
                    "incorrect username or password",HttpStatus.BAD_REQUEST
            );
        }

    }

}
