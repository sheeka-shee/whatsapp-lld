Whatsapp LLD

functional requirements:
- users should be able to send a message
- user should be able to view a message
- user should be able to search the chats
- user should get push over notification when not online

--out-of-scope--
- video call
- read receipts
- profile creation
- authentication
- payment
- status

entities:
- chat
- user
- message
- notification

class design
chat
- id
- name
- particpantIds: List<String> partcipantIds

user
- id
- name
- phone 

message
- id
- content
- senderId - User
- chatId
- status : MessageStatus

enum MessageStatus
- SENT
- DELIVERED

class MessageRepository
- chatToChatMessages <chatId, List<Message>)
- userToPendingMessages <userId, List<Message>)
+ addPendingMessage(userId, Message)
+ fetchPendingMessages(userId)
+ deletePendingMessage(userId)
+ saveMessage(message)
+ getMessagesByChatId(chatId)

class ChatRepository
- userToChats: Map<userId, List<String> chatIds)
- chatIdToChat: Map<chatId, Chat)
+ saveChat(chat)
+ getChatById(chatId)
+ getChatsByUserId(userId)
// getParticipants(chatId)

class SessionManager
- onlineUsers: Set<String userId>
+ isUserOnline(userId) -> boolean
+ setOnline(userId)
+ setOffline(userId)

class ChatService
// fetchMessagesByChatId(chatId) -> List<Message>
// fetchChatsByUserId(userId) -> List<Chat>
+ createChat(chatId, name, Set<String> participantIds) -> Chat
+ sendMessage(chatId, msgId, content, senderId)  
    chat = ChatRepository.getChatById(chatId), If null -> throw exception
    message = new Message(message)
    MessageRepository.save(message)
    
    participantIds = chat.getParticipantIds();

    for p in participants:
        if p is sender
            continue
        else if (isUserOnline(p))
            // deliverMessage(p.id, Message)
            // sendDeliverAckToUser(p.id, senderId, Message)
            message.setStatus(Delivered)
            print -> content
        else
          MessageRepository.addPendingMessage(p, message)
          NotificationService.pushNotification(p, Message)
 + userLogsIn(userId)
       SessionManager.setOnline(userId)
       List<Message> messages = MessageRepository.fetchAndDeletePendingMessage(userId)
       messages -> setStatus = DELIVERED
 + userLogsOut(userId)
       SessionManager.setOffline(userId)

NotificationService
+ sendPushNotification(userId, Message)

SearchService
+ searchMessagesInChat(chatId, keyword)
      MessageRepository.getMessagesByChatId(search by converting both content and keyword in lowercase using stream)
    
<img width="1086" height="605" alt="image" src="https://github.com/user-attachments/assets/5408c479-22d3-4fcf-a2ca-43c3463145ea" />



