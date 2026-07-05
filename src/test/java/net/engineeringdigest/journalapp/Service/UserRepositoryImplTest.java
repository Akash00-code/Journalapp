package net.engineeringdigest.journalapp.Service;

import net.engineeringdigest.journalapp.JournalMongodbRepo.UserRepositoryImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryImplTest {

    @Autowired
    UserRepositoryImpl userRepositoryImpl;

    @Test
    public void testRepo() {
        assertNotNull(userRepositoryImpl.getUsersForSA());
    }
}
