package com.mywebapp.application.endpoints;

import com.mywebapp.application.endpoints.Health;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Unit tests for the Health endpoint.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@TestPropertySource()
class HealthTest {

    @Autowired
    Health health;

    /**
     * Test to ensure initialization of Health endpoint.
     */
    @Test
    public void testInitialization(){
        assertNotEquals(health,null);
    }

    /**
     * Test to verify the status code of the Health endpoint.
     */
    @Test
    public void testHealthStatus(){
        assertEquals(health.getHealthz().getStatusCode().value(),200);
    }
}
