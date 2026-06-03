package org.example.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private final Set<String> onlineUsers = ConcurrentHashMap.newKeySet();

    public void setOnline(String userId) {
        onlineUsers.add(userId);
    }

    public void setOffline(String userId) {
        onlineUsers.remove(userId);
    }

    public boolean isUserOnline(String userId) {
        return onlineUsers.contains(userId);
    }
}
