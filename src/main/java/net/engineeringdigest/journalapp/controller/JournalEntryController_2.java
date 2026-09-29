package net.engineeringdigest.journalapp.controller;

import java.util.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import javax.swing.text.html.HTML;

@RestController
@RequestMapping("/Journal")
@Tag(name = "Journal APIs",description = "Journal controller")
public class JournalEntryController_2 {

    @Autowired
    private JournalServices mongoService;
    @Autowired
    private UserService  userService;

    @PostMapping
    @Operation(summary = "create an journal entry for user")
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
    @Operation(summary = "get all journal entries of a user")
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
    @Operation(summary = "get journal entry by id")
    public ResponseEntity<?> getEntryById(@PathVariable String jid) {
        ObjectId id =new ObjectId(jid);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        JournalEntry journalEntry = user.getJournalentries().stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
        if(journalEntry != null){
            return new ResponseEntity<>(journalEntry, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/id/{id}")
    @Operation(summary = "delete entry by id")
    public ResponseEntity<?> DeleteEntry(@PathVariable String jid) {
        ObjectId id=new ObjectId(jid);
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
    @Operation(summary = "delete all entries")
    public ResponseEntity<?> deletemanyEntry(){
        mongoService.deletemany();
        return new ResponseEntity<>(HttpStatus.GONE);
    }


}
