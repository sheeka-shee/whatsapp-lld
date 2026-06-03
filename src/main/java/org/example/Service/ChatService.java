package org.example.Service;

import org.example.Enum.MessageStatus;
import org.example.Model.Chat;
import org.example.Model.Message;
import org.example.Repository.ChatRepository;
import org.example.Repository.MessageRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final SessionManager sessionManager;
    private final NotificationService notificationService;

    public ChatService(ChatRepository chatRepository, MessageRepository messageRepository, SessionManager sessionManager, NotificationService notificationService) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.sessionManager = sessionManager;
        this.notificationService = notificationService;
    }

    public Chat createChat(String id, String name, Set<String> participantIds) {
        Chat chat = new Chat(id, name, participantIds);
        chatRepository.saveChat(chat);
        return chat;
    }

    public void sendMessage(String msgId, String chatId, String senderId, String content) {
        Chat chat = chatRepository.getChatById(chatId);
        if(chat == null) throw new IllegalArgumentException("Chat context not found!");

        Message message = new Message(msgId, chatId, senderId, content);
        messageRepository.saveMessage(message);

        for(String participantId: chat.getParticipantIds()) {
            if(participantId.equals(senderId)) continue;

            if(sessionManager.isUserOnline(participantId)) {
                message.setStatus(MessageStatus.DELIVERED);
                System.out.println("[Direct Websocket delivery] to User " + participantId + " -> " + content);
            } else {
                messageRepository.addPendingMessage(participantId, message);
                notificationService.sendPushNotifications(participantId, message);
            }
        }
    }

    public List<Message> userLogsIn(String userId) {
        sessionManager.setOnline(userId);
        List<Message> missedMessages = messageRepository.fetchAndClearPendingMessage(userId);
        for(Message m: missedMessages) {
            m.setStatus(MessageStatus.DELIVERED);
        }
        return missedMessages;
    }

    public void userLogsOut(String userId) {
        sessionManager.setOffline(userId);
    }
}
