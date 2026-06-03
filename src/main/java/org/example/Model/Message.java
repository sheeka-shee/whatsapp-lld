package org.example.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Enum.MessageStatus;

@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Setter
public class Message {
        @Id
        private final String id;
        private final String content;
        private final String senderId;
        private final String chatId;
        private MessageStatus status; //can't be final because we will change its state
        private final Long timestamp;

        public Message(String id, String chatId, String senderId, String content) {
                this.id = id;
                this.chatId = chatId;
                this.senderId = senderId;
                this.content = content;
                this.timestamp = System.currentTimeMillis();
                this.status = MessageStatus.SENT;
        }

        @Override
        public String toString() {
                return "[" + status + "]" + senderId + ": " + content;
        }
}
