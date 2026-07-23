package net.engineeringdigest.journalapp.Service;

import net.engineeringdigest.journalapp.schedular.UserShcheduler;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserSchedulerTest {
    @Autowired
    private UserShcheduler userScheduler;
    @Test
    public void testUserScheduler()
    {
        userScheduler.fetchUsersAndSendSAMail();
    }
}
