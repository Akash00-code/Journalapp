package net.engineeringdigest.journalapp.Entity;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import net.engineeringdigest.journalapp.enums.Sentiment;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

import java.time.LocalDateTime;

@Document(collection = "Journal_entries")
@Data
public class JournalEntry {

    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
    private Sentiment sentiment;

}
