package org.example.Service;

import org.example.Model.Message;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    public void sendPushNotifications(String userId, Message message) {
        System.out.println(" [Push Notification Sent to " + userId + "]: New text from " + message.getSenderId() );
    }
}
