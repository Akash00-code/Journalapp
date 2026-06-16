package net.engineeringdigest.journalapp.controller;

import java.util.*;

import net.engineeringdigest.journalapp.Entity.User;
import net.engineeringdigest.journalapp.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.engineeringdigest.journalapp.Entity.JournalEntry;
import net.engineeringdigest.journalapp.Service.JournalServices;

@RestController
@RequestMapping("/Journal")
public class JournalEntryController_2 {

    @Autowired
    private JournalServices mongoService;
    @Autowired
    private UserService  userService;

    @PostMapping
    public ResponseEntity<?> CreateEntry(@RequestBody JournalEntry e) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            mongoService.SaveEntry(e,authentication.getName());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception exp) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping
    public ResponseEntity<?> getEntriesList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        List<JournalEntry> all = user.getJournalentries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getEntryById(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        JournalEntry journalEntry = user.getJournalentries().stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
        if(journalEntry != null){
            return new ResponseEntity<>(journalEntry, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> DeleteEntry(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        boolean b = user.getJournalentries().removeIf(x -> x.getId().equals(id));
        if(b){
            userService.SaveUser(user);
            mongoService.DeleteEntryBYid(id);
            return new ResponseEntity<>("Entry deleted successfully",HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> UpdateEntry(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        JournalEntry old = user.getJournalentries().stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
        if (old != null) {
            old.setTitle(!newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent()
                    : old.getContent());
            mongoService.SaveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @DeleteMapping
    public ResponseEntity<?> deletemanyEntry(){
        mongoService.deletemany();
        return new ResponseEntity<>(HttpStatus.GONE);
    }


}
