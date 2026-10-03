package net.engineeringdigest.journalapp.DTO;

import com.mongodb.lang.NonNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotEmpty
    private String userName;
    private String email;
    private Boolean sentimentAnalysis;
    @NotEmpty
    private String password;
}
