package net.engineeringdigest.journalapp.Entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ConfigJournal")
@Data
public class ConfigJournalAppEntity {

    @Id
    ObjectId id;

    String key;
    String value;
}
