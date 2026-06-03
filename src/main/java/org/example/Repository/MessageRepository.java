package org.example.Repository;

import org.example.Model.Message;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MessageRepository {
    private final Map<String, List<Message>> chatMessages = new ConcurrentHashMap<>();
    private final Map<String, List<Message>> pendingMessages = new ConcurrentHashMap<>();

    public void saveMessage(Message message) {
        chatMessages.computeIfAbsent(message.getChatId(), k->new ArrayList<>()).add(message);
    }

    public List<Message> getMessagesByChatId(String chatId) {
        return chatMessages.getOrDefault(chatId, Collections.emptyList());
    }

    public void addPendingMessage(String userId, Message message) {
        pendingMessages.computeIfAbsent(userId, k->new ArrayList<>()).add(message);
    }

    public List<Message> fetchAndClearPendingMessage(String userId) {
        List<Message> pending = pendingMessages.getOrDefault(userId, new ArrayList<>());
        pendingMessages.remove(userId);
        return pending;
    }
}
