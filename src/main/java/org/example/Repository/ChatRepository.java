package org.example.Repository;

import org.example.Model.Chat;
import org.example.Model.Message;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class ChatRepository {
    private final Map<String, Chat> chats = new ConcurrentHashMap<>();
    private final Map<String, List<String>> userToChatIds = new ConcurrentHashMap<>();

    public void saveChat(Chat chat) {
        chats.put(chat.getId(), chat);
        for(String userId: chat.getParticipantIds()) {
                userToChatIds.computeIfAbsent(userId, k->new ArrayList<>()).add(chat.getId());
        }
    }

    public Chat getChatById(String chatId) {
        return chats.get(chatId);
    }

    public List<Chat> getChatsByUserId(String userId) {
        List<String> chatIds = userToChatIds.getOrDefault(userId, Collections.emptyList());
        return chatIds.stream().map(chats::get).collect(Collectors.toList());
    }
}
