package com.mywebapp.application.security;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for DynamoDB setup.
 */
@Configuration
@EnableDynamoDBRepositories(basePackages = "com.mywebapp.application.repository.TokenRepository")
public class DynamoDbConfig {

   @Value("${amazon.dynamodb.endpoint}")
   private String amazonDynamoDBEndpoint;

   @Value("${amazon.aws.accesskey}")
   private String amazonAWSAccessKey;

   @Value("${amazon.aws.secretkey}")
   private String amazonAWSSecretKey;

   /**
    * Creates an AmazonDynamoDB bean with the provided AWS credentials.
    * @param awsCredentialsProvider The AWS credentials provider.
    * @return An instance of AmazonDynamoDB.
    */
   @Bean
   public AmazonDynamoDB amazonDynamoDB(AWSCredentialsProvider awsCredentialsProvider) {
       AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
               .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, "us-west-2"))
               .withCredentials(awsCredentialsProvider).build();
       return amazonDynamoDB;
   }

   /**
    * Creates an AWS credentials provider bean with the provided access and secret keys.
    * @return An instance of AWSCredentialsProvider.
    */
   @Bean
   public AWSCredentialsProvider awsCredentialsProvider() {
       return new AWSStaticCredentialsProvider(new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey));
   }
}
