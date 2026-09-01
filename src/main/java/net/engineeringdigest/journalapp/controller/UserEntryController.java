package net.engineeringdigest.journalapp.controller;



import net.engineeringdigest.journalapp.Entity.Quotes;
import net.engineeringdigest.journalapp.Entity.WeatherResponse;
import net.engineeringdigest.journalapp.Service.QuotesService;
import net.engineeringdigest.journalapp.Service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.*;

import net.engineeringdigest.journalapp.Entity.User;
import net.engineeringdigest.journalapp.Service.UserService;

@RestController
@RequestMapping("/user")
public class UserEntryController {

    @Autowired
    private UserService userService;
    public PasswordEncoder encoder=new BCryptPasswordEncoder();
    @Autowired
    WeatherService  weatherService;
    @Autowired
    QuotesService quotesService;
    @GetMapping("/my-profile")
    public User userInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        System.out.println(STR."Clients remote address: \{details.getRemoteAddress()}\n session_id: \{details.getSessionId()}");
        return userService.findByUserName(authentication.getName());
    }

    @PutMapping
    public ResponseEntity<?> UpdateUser(@RequestBody User u) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        if (user != null) {
            user.setUserName(u.getUserName());
            user.setPassword(encoder.encode(u.getPassword()));
            user.setRoles(u.getRoles());
            userService.SaveUser(user);
        }
        return new ResponseEntity<>("Updated successfully",HttpStatus.NO_CONTENT);
    }
    @DeleteMapping
    public ResponseEntity<?> DeleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.DeleteByUsername(authentication.getName());
        return new ResponseEntity<>("Deleted successfully",HttpStatus.GONE);
    }
    @DeleteMapping("/DeleteAllUsers")
    public ResponseEntity<?> DeleteAllUsers(){
        userService.deletemany();
        return new ResponseEntity<>(HttpStatus.GONE);
    }
    @GetMapping("/weather/{city}")
    public ResponseEntity<?> getWeather(@PathVariable String city){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse currentWeather = weatherService.getCurrentWeather(city);
        String response="";
        if (currentWeather != null){
            response= STR."hi \{authentication.getName()} today temperature at \{city} is \{currentWeather.getCurrent().getTemperature()}°C and it feelslike \{currentWeather.getCurrent().getFeelsLike()}°C";
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> greet(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Quotes quotes = quotesService.getQuotes();
        String response="";
        if(quotes!=null){
            response= """
                    Hello %s welcome
                    
                    %s
                    -by %s""".formatted(authentication.getName(),quotes.getQuote(),quotes.getAuthor());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
