package net.engineeringdigest.journalapp.MockitoTests;

import net.engineeringdigest.journalapp.Entity.User;
import net.engineeringdigest.journalapp.JournalMongodbRepo.UserRepo;
import net.engineeringdigest.journalapp.Service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class UserDetailsServiceTest {
    @InjectMocks
    CustomUserDetailsService userDetailsService;

    @Mock
    UserRepo userRepo;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findUserByUserNameTest() {
        when(userRepo.findByUserName(ArgumentMatchers.anyString()))
                .thenReturn(User.builder()
                        .userName("anything")
                        .password("i don't know")
                        .roles(List.of("USER","ADMIN"))
                        .build()).thenReturn(User.builder().userName("hello").password("helo100").roles(new ArrayList<>()).build());
        UserDetails user=userDetailsService.loadUserByUsername("bueieff");
        UserDetails user1=userDetailsService.loadUserByUsername("hello");
        assertNotNull(user,()->"UserDetails is null");
        assertEquals("hello", user1.getUsername(),()->"Username doesn't matched");
    }

}
