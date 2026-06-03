package org.example.Service;

import org.example.Model.Message;
import org.example.Repository.MessageRepository;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private final MessageRepository messageRepository;

    public SearchService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> searchMessagesInChat(String chatId, String keyword) {
        return messageRepository.getMessagesByChatId(chatId).stream()
                .filter(m->m.getContent().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}
