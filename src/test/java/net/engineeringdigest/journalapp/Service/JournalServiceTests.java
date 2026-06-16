package net.engineeringdigest.journalapp.Service;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


public class JournalServiceTests {


    @Test
    void test(){
        assertTimeout(Duration.ofMillis(100), LoopTimeout::runLoopTimeout);
    }
    @RepeatedTest(value = 5,
            name = "Repetition {currentRepetition} of {totalRepetitions} ")
    void testRepeatedly(){
        assertEquals(9,LoopTimeout.Square(3));
    }
}
