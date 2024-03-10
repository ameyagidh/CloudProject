package com.mywebapp.application.objects;

import software.amazon.awssdk.services.sns.model.MessageAttributeValue;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to build a PublishRequest for AWS SNS service.
 */
public class RequestBuilder {

    /**
     * Builds a PublishRequest with the specified topic ARN and message attributes.
     * 
     * @param topicArn The ARN of the SNS topic.
     * @param message  The message object containing email, token, and message type.
     * @return PublishRequest object ready to be sent to SNS.
     */
    public static PublishRequest build(String topicArn, Message message) {
        // Build message attributes
        Map<String, MessageAttributeValue> attributes = new HashMap<>();
        attributes.put("email", buildAttribute(message.getEmail(), "String"));
        attributes.put("token", buildAttribute(message.getToken(), "String"));
        attributes.put("messagetype", buildAttribute(message.getMessagetype(), "String"));

        // Build PublishRequest
        PublishRequest request = PublishRequest.builder()
                .topicArn(topicArn)
                .message("text message from ebby") // Default message text
                .messageAttributes(attributes)
                .build();

        return request;
    }

    /**
     * Builds a MessageAttributeValue object with the specified value and data type.
     * 
     * @param value    The attribute value.
     * @param dataType The data type of the attribute (e.g., String).
     * @return MessageAttributeValue object.
     */
    private static MessageAttributeValue buildAttribute(String value, String dataType) {
        return MessageAttributeValue.builder()
                .dataType(dataType)
                .stringValue(value)
                .build();
    }
}
