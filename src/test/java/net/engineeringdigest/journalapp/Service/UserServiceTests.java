package net.engineeringdigest.journalapp.Service;

import net.engineeringdigest.journalapp.Entity.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserService userService;

    @BeforeAll
    static void setup() {
        System.out.println("setup all tests");
    }
    @BeforeEach
    void setUp() {
        System.out.println("Setting up UserServiceTests");
    }

    @Test
    void ArithmeticTest(){
        assertEquals(5,10/2,()->"expected not matched the actual");
        assertNotEquals(4,10/5);
    }
    @Test
    void SaveUserTest(){
        assertFalse(userService.SaveUser(null));
    }
    @Test
    void ArraysTest(){
        int[] expected={1,2,3,4,5};
        int [] actual={1,3,2,4,5};
        Arrays.sort(actual);
        assertArrayEquals(expected,actual);
    }
    @AfterEach
    void tearDown(){
        System.out.println("Tearing down UserServiceTests");
    }

    @AfterAll
    static void tearDownAll(){
        System.out.println("Tearing down UserServiceTests all");
    }
    @Nested
    class Allparameterizedtests{
        @ParameterizedTest
        @CsvSource({"akash",
                "Bob",
                "Alice","Cesar"
        })
        void FindByUserNameTest(String name){
            assertNull(userService.findByUserName(name),()->"Found null");
        }
        @ParameterizedTest
        @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10})
        void testSquare(int input){
            assertEquals(input*input,LoopTimeout.Square(input));
        }
        @ParameterizedTest
        @MethodSource("provideArguments")
        void testSquareTest(int input){
            assertEquals(input*input,LoopTimeout.Square(input));
        }
        static List<Integer> provideArguments(){
            return new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
        }
        @ParameterizedTest
        @ArgumentsSource(CustomArgumentsProvider.class)
        void testCustomArgumentsProvider(User user){
            assertTrue(userService.SaveUser(user));
        }
    }
}
