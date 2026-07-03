package net.engineeringdigest.journalapp.Entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Data;

@Document(collection = "users")
@Data
@Builder
public class User {
    @Id
    private ObjectId ID;

    @NonNull
    @Indexed(unique = true)
    private String userName;
    private String email;
    private boolean sentimentAnalysis;
    @NonNull
    private String password;
    @DBRef
    private List<JournalEntry> Journalentries = new ArrayList<>();
    private List<String> roles;

}
