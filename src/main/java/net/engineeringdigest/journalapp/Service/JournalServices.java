package net.engineeringdigest.journalapp.Service;

import net.engineeringdigest.journalapp.Entity.JournalEntry;

import java.util.List;
import java.util.Optional;

import net.engineeringdigest.journalapp.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.engineeringdigest.journalapp.JournalMongodbRepo.JournalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.RuntimeErrorException;

@Service
public class JournalServices {

    @Autowired
    private JournalRepository JP;

    @Autowired
    private UserService US;
    @Transactional
    public void SaveEntry(JournalEntry e, String username) {
        try{
            User user = US.findByUserName(username);
            JournalEntry saved = JP.save(e);
            user.getJournalentries().add(saved);
            //user.setUserName(null);
            US.SaveUser(user);

        }catch(NullPointerException ex){
            System.out.println(ex);
        }


    }
    public void SaveEntry(JournalEntry e) {
        JP.save(e);
    }

    public List<JournalEntry> GetAllEntries() {
        return JP.findAll();

    }

    public Optional<JournalEntry> getEntry(ObjectId id) {
        return JP.findById(id);
    }

    public void DeleteEntryBYid(ObjectId id) {
        JP.deleteById(id);
    }

    public void deletemany(){
        JP.deleteAll();
    }
}
