package net.engineeringdigest.journalapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.engineeringdigest.journalapp.Entity.JournalEntry;

interface MongoRepo extends MongoRepository<JournalEntry, Long> { // this is not the best practice
                                                                  // we should use service in which our logic is written
                                                                  // and a repository where we have mongodb operations.
                                                                  // so controller -----> service ----> mongorepo

}

@RestController
@RequestMapping("/_Journal")
public class JournalEntryController {

    @Autowired
    private MongoRepo mdb;

    @GetMapping
    public List<JournalEntry> getEntries() {
        return null;
    }

    @PostMapping
    public boolean CreateEntry(@RequestBody JournalEntry e) {
        mdb.save(e);
        return true;
    }

    @DeleteMapping("/id/{Myid}")
    public boolean DeleteEntry(@PathVariable Long Myid) {

        return true;
    }

    @GetMapping("/id/{Myid}")
    public JournalEntry getEntry(@PathVariable Long Myid) {
        return null;
    }

    @PutMapping("/id/{id}")
    public boolean RenameEntry(@PathVariable Long id, @RequestBody JournalEntry e) {
        return true;
    }

}
