package com.mywebapp.application.services;

import com.mywebapp.application.objects.Message;
import software.amazon.awssdk.services.sns.model.SnsResponse;

/**
 * Interface for publishing messages using a message broker or service.
 */
public interface MessagePublisher {
    /**
     * Publishes a message.
     *
     * @param message The message to be published.
     */
    void publish(Message message);
}
