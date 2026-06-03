package org.example;

import org.example.Model.Chat;
import org.example.Model.Message;
import org.example.Model.User;
import org.example.Repository.ChatRepository;
import org.example.Repository.MessageRepository;
import org.example.Service.ChatService;
import org.example.Service.NotificationService;
import org.example.Service.SearchService;
import org.example.Service.SessionManager;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        ChatRepository chatRepo = new ChatRepository();
        MessageRepository messageRepo = new MessageRepository();
        SessionManager sessionManager = new SessionManager();
        NotificationService notificationService = new NotificationService();

        ChatService chatService = new ChatService(chatRepo, messageRepo, sessionManager, notificationService);
        SearchService searchService = new SearchService(messageRepo);

        User alice = new User("u1", "Alice", "111");
        User bob = new User("u2", "Bob", "222");

        System.out.println("Scenario 1: Alice and Bob both are online");
        sessionManager.setOnline("u1");
        sessionManager.setOnline("u2");

        Chat privateChat = chatService.createChat("c1", "Alice & Bob", new HashSet<>(Arrays.asList(alice.getId(), bob.getId())));

        chatService.sendMessage("m1","c1","u1","Hello Bob");

        System.out.println("Scenario 2: Bob drops offline, Alice pings again");
        sessionManager.setOffline(bob.getId());

        chatService.sendMessage("m2", "c1", "u1", "Hey, you there?");
        chatService.sendMessage("m3", "c1", "u1", "Let me know when you see this");

        System.out.println("Scenario 3: Bob comes back online, and pulls the pending messages");
        sessionManager.setOnline(bob.getId());
        List<Message> missedByBob = messageRepo.fetchAndClearPendingMessage(bob.getId());
        System.out.println("Bob's app synced offline items: " + missedByBob);

        System.out.println("Scenario 4: Searching conversations for keywords");
        List<Message> results = searchService.searchMessagesInChat("c1", "there");
        System.out.println("Search results for keyword 'there' are: " + results);
    }
}