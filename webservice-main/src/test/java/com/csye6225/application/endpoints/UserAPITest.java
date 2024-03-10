package com.mywebapp.application.endpoints;

import com.mywebapp.application.MetricRegistry;
import com.mywebapp.application.objects.ErrorResponse;
import com.mywebapp.application.objects.User;
import com.mywebapp.application.repository.UserRepository;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the UserAPI endpoint.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class UserAPITest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserAPI userAPI;

    @InjectMocks
    MetricRegistry metricRegistry;

    /**
     * Test to ensure initialization of UserAPI.
     */
    @Test
    public void testInitialization(){
        assertNotEquals(userAPI,null);
    }

    /**
     * Method to initialize mocks before each test.
     */
    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test to create a new user.
     */
    @Test
    public void testCreateUser() {
        User user = User.builder().username("username@gmail.com")
                .password("password").firstName("First").lastName("Last").build();
        ResponseEntity responseEntity = userAPI.createUser(user);
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("mockedpassword");
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(null);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());
    }

    /**
     * Test to create an existing user.
     */
    @Test
    public void testCreateExistingUser() {
        User user = User.builder().username("username@gmail.com")
                .password("password").firstName("First").lastName("Last").build();
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("mockedpassword");
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(user);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        ResponseEntity responseEntity = userAPI.createUser(user);
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());
        assertEquals(ErrorResponse.class, responseEntity.getBody().getClass());
    }
}
