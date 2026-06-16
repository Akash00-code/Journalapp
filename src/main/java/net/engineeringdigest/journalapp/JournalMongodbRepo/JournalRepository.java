package net.engineeringdigest.journalapp.JournalMongodbRepo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


import net.engineeringdigest.journalapp.Entity.JournalEntry;


public interface JournalRepository extends MongoRepository<JournalEntry, ObjectId> {

}
