package com.mywebapp.application.endpoints;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.mywebapp.application.MetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Service endpoint for checking the health status of the application.
 */
@Service
@RestController
@RequestMapping("/healthz")
public class Health {
    private static final Logger LOGGER = LoggerFactory.getLogger(Health.class);

    @Autowired
    MetricRegistry metricRegistry;

//    @Autowired
//    AmazonDynamoDB client;

    /**
     * Endpoint to get the health status of the application.
     * @return ResponseEntity representing the health status.
     */
    @GetMapping()
    public ResponseEntity<?> getHealthz() {
        // Increment counter for health endpoint in metric registry
        metricRegistry.getInstance().counter("Health get", "mywebapp", "health endpoint").increment();
        // Log health endpoint call
        LOGGER.info("Health endpoint called");
        // Return ok response with null body
        return ResponseEntity.ok().body(null);
    }

    /**
     * Additional health endpoint for future use.
     * @return ResponseEntity representing the health status.
     */
    @GetMapping(value = "/health2")
    public ResponseEntity<?> getHealth(){

        return ResponseEntity.ok().body(null);
    }
}
