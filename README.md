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
- messages: List<Message>
- particpants: List<User>

user
- id
- name
- phone 
- contactList: List<User>

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
+ userToPendingMessage<string name, List<Message>)
+ addPendingMessage(userId, Message)
+ fetchPendingMessages(userId)
+ deletePendingMessage(userId)
+ saveMessage()

class ChatRepository
- userToChats: Map<userId, List<Chat>)
- chatIdToChat: Map<chatId, Chat)
- getChatById(chatId)
- getParticipants(chatId)

class DiscoveryController
- userDiscoveryMap: Map<string, boolean>
+ isUserDiscoverable(userId) -> boolean

class ChatController
+ fetchMessagesByChatId(chatId) -> List<Message>
+ fetchChatsByUserId(userId) -> List<Chat>
+ createChat(List<User> particpants, name) -> Chat
+ sendMessage(chatId, Message, senderId)  
    message = new Message(message)
    chat = ChatRepository.getChatById(chatId)
    
    participants = chat.getParticipants();

    for p in participants:
        if p is sender
            continue
        else if (isUserDiscoverable(p))
            deliverMessage(p.id, Message)
            sendDeliverAckToUser(p.id, senderId, Message)
        else NotificationService.pushNotification(p.Id, Message)
  
+ fetchPendingMessages(userId) -> List<Message>
+ deliverMessage(userId, Message) -> "Message delivered to userId" 
+ sendDeliverAckToUser(receipient.Id, senderId, Message)

NotificationService
+ sendPushNotification(userId, Message)
    



