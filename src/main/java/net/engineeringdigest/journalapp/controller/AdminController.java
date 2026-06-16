package net.engineeringdigest.journalapp.controller;


import net.engineeringdigest.journalapp.Entity.User;
import net.engineeringdigest.journalapp.Service.UserService;
import net.engineeringdigest.journalapp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService service;
    @Autowired
    AppCache cache;
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<User> all = service.getAll();
        if(all.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody User admin){
        service.SaveAdmin(admin);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping
    public String clearCache(){
        cache.init();
        return  STR."cache is freed.";
    }
}
