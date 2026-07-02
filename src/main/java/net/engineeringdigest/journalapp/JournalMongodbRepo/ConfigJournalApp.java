package net.engineeringdigest.journalapp.JournalMongodbRepo;

import net.engineeringdigest.journalapp.Entity.ConfigJournalAppEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ConfigJournalApp extends MongoRepository<ConfigJournalAppEntity, ObjectId> {
}
