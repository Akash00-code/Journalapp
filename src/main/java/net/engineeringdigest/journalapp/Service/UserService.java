package net.engineeringdigest.journalapp.Service;

import java.util.List;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalapp.Entity.JournalEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import net.engineeringdigest.journalapp.Entity.User;
import net.engineeringdigest.journalapp.JournalMongodbRepo.UserRepo;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepo userRepository;
    @Autowired
    private PasswordEncoder encoder;
    //Logger logger=LoggerFactory.getLogger(UserService.class);
    public boolean SaveUser(User user) {
        if(user==null) {return false;}
        try{
            User save = userRepository.save(user);
        }catch(Exception e){
            log.error("Error saving user");
            log.info("Error saving user");
            log.warn("Error saving user");
        }
        return true;
    }
    public void SaveAdmin(User admin){
        admin.setPassword(encoder.encode(admin.getPassword()));
        admin.setRoles(List.of("USER","ADMIN"));
        userRepository.save(admin);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
    public User findByUserName(String user){
       return userRepository.findByUserName(user);
    }
    public void DeleteByUsername(String username){
        User byUserName = findByUserName(username);
        List<JournalEntry> journalentries = byUserName.getJournalentries();
        byUserName.getJournalentries().removeAll(journalentries);
        userRepository.deleteByUserName(username);
    }
    public void deletemany(){
        userRepository.deleteAll();
    }

}
