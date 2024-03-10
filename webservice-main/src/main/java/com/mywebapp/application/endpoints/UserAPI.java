package com.mywebapp.application.endpoints;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.mywebapp.application.MetricRegistry;
import com.mywebapp.application.objects.*;
import com.mywebapp.application.repository.ImageRepository;
import com.mywebapp.application.repository.UserRepository;
import com.mywebapp.application.security.BucketCreated;
import com.mywebapp.application.security.BucketName;
import com.mywebapp.application.security.TokenUtils;
import com.mywebapp.application.services.ImageServiceImpl;
import com.mywebapp.application.services.MessagePublisherImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.SnsClientBuilder;
import software.amazon.awssdk.services.sns.model.SnsResponse;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * RESTful API endpoints for user-related operations.
 */
@Service
@RestController
@RequestMapping("/v1")
public class UserAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAPI.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageServiceImpl service;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenUtils tokenUtils;

    @Value("${cloud.aws.bucketname}")
    private String bucketName;

    @Autowired
    MetricRegistry metricRegistry;

    @Autowired
    MessagePublisherImpl messagePublisher;

    private DynamoDB dynamoDB;

    private static String tableName = "emailTokenTbl";
    private AmazonDynamoDB client;

    @PostConstruct
    void init(){
        client =  AmazonDynamoDBClientBuilder.standard().withCredentials(new InstanceProfileCredentialsProvider(false))
                .withRegion("us-east-1").build();

        dynamoDB = new DynamoDB(client);
    }

    /**
     * Endpoint for creating a new user.
     * @param user The user object to be created.
     * @return ResponseEntity containing the created user object.
     */
    @PostMapping(value = "/user",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity createUser(@RequestBody User user){
        try {
            metricRegistry.getInstance().counter("Create User","mywebapp","createuser endpoint").increment();
            LOGGER.info("Creating a user endpoint called");

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User temp = userRepository.findByUsername(user.getUsername());
            if (userRepository.findByUsername(user.getUsername()) == null) {
                user.setVerified(false);
                userRepository.save(user);
                User userLatest = userRepository.findByUsername(user.getUsername());

                Table table = dynamoDB.getTable(tableName);
                String tkn = generateUniqueId();
                Item item = new Item().withString("emailid",user.getUsername())
                        .withString("email", user.getUsername())
                        .withLong("ttl",(System.currentTimeMillis() / 1000L)+ 120)
                        .withString("token",tkn);
                table.putItem(item);

                Message message = new Message(user.getUsername(),tkn,"publish message");

                messagePublisher.publish(message);
                LOGGER.info("Message published");

                return ResponseEntity.status(HttpStatus.CREATED).body(userLatest);
            } else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("username already exists, try a different one"));
        } catch (Exception e){
            LOGGER.error(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Unable to create user, check if input data is correct"));
        }
    }

    /**
     * Endpoint for verifying a user's email.
     * @param email The email of the user to be verified.
     * @param token The verification token.
     * @return ResponseEntity containing the verification result.
     */
    @GetMapping(value = "/verifyUserEmail",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> verifyUser(@RequestParam("email") String email, @RequestParam("token") String token ){

        metricRegistry.getInstance().counter("verifyUser get","mywebapp","verifyUser endpoint").increment();
        LOGGER.info("verifyUser requested"+email + " " +token);
        Table table = dynamoDB.getTable(tableName);
        try {
            Map<String,Object> itemmap = table.getItem("emailid", email).asMap();

            if(Long.parseLong(itemmap.get("ttl").toString()) < (System.currentTimeMillis() / 1000L)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( "Token Expired");
            }
            if(itemmap.get("emailid").equals(email) && itemmap.get("token").equals(token)){
                User presentUser = userRepository.findByUsername(email);

                if(presentUser.getVerified())
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body( "User has already been verified");

                presentUser.setVerified(true);
                userRepository.save(presentUser);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body( "User has been verified successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( "User cannot be verified. Please check the credentials");
            }
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( "User cannot be verified. Please check the credentials");
        }
    }

}
