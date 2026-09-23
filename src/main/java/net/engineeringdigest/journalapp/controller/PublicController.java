package net.engineeringdigest.journalapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalapp.Entity.User;
import net.engineeringdigest.journalapp.Service.EmailService;
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

import java.security.SecureRandom;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {
    private  final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;
    public static final ConcurrentHashMap<String, Instant> otpMap=new ConcurrentHashMap<>();

    public PublicController(AuthenticationManager authenticationManager, JwtService jwtService,EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }

    @GetMapping("/health-check")
    public String Health() {
        return STR."App is healthy";
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
        if(user==null||user.getEmail().isBlank()||!(user.getEmail().equals(Newuser.getEmail()))){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        SecureRandom random = new SecureRandom();
        int otp=100000+random.nextInt(999999);
        String OTP=String.valueOf(otp);

        String response = emailService.sendOtpEmail(user.getEmail(),
                "OTP for changing password",
                STR."OTP to change your account password is \{OTP}");
        otpMap.put(OTP,Instant.now().plusSeconds(90));
        if(response.equals("success")){
            return new ResponseEntity<>("OTP is sent to your registered email",HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong Please try again later",HttpStatus.SERVICE_UNAVAILABLE);
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
    @GetMapping("/validateOtp")
    public String validateOtp(@RequestParam String Otp,@RequestBody User user){
        if(!otpMap.containsKey(Otp)||otpMap.get(Otp)==null){
            return "Please generate your OTP";
        }
        if(otpMap.get(Otp).isBefore(Instant.now())){
            otpMap.remove(Otp);
            return "OTP is expired";
        }
        otpMap.remove(Otp);
        User nuser = userService.findByUserName(user.getUserName());
        nuser.setPassword(encoder.encode(user.getPassword()));
        userService.SaveUser(nuser);
        return "password changed successfully";
    }

}
