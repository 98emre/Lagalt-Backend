package project.lagalt.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.lagalt.model.entities.Collaborator;
import project.lagalt.model.entities.Message;
import project.lagalt.model.entities.User;
import project.lagalt.repository.MessageRepository;
import project.lagalt.serviceImpl.MessageServiceImpl;
import project.lagalt.utilites.enums.Application;
import project.lagalt.utilites.enums.MessageStatus;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @InjectMocks
    private MessageServiceImpl messageService;

    @Mock
    private MessageRepository messageRepository;

    @Test
    void test_Add_Message_Successfully(){
        Message mockMessage = new Message();
        mockMessage.setText("Hello Emre");
        mockMessage.setMessageStatus(MessageStatus.UNREAD);

        when(messageRepository.save(any(Message.class))).thenReturn(mockMessage);

        Message addMessage = messageService.add(mockMessage);

        assertThat(addMessage).isNotNull();
        assertThat(addMessage.getText()).isEqualTo("Hello Emre");
    }

    @Test
    void test_Find_Message_By_Id() {
        Message mockMessage = new Message();
        mockMessage.setText("Hello Emre");
        mockMessage.setMessageStatus(MessageStatus.UNREAD);

        when(messageRepository.findById(1)).thenReturn(Optional.of(mockMessage));

        Message foundMessage = messageService.findById(1);

        assertThat(foundMessage).isNotNull();
        assertThat(foundMessage.getText()).isEqualTo("Hello Emre");

    }

    @Test
    void test_Find_All_Messages() {
        Message mockMessage = new Message();
        mockMessage.setText("Hello Emre");
        mockMessage.setMessageStatus(MessageStatus.UNREAD);

        Message mockMessage2 = new Message();
        mockMessage2.setText("Hello Emre 2");
        mockMessage2.setMessageStatus(MessageStatus.UNREAD);

        List<Message> mockMessages = Arrays.asList(mockMessage,mockMessage2);

        when(messageRepository.findAll()).thenReturn(mockMessages);

        List<Message> messages = (List<Message>) messageService.findAll();

        assertThat(messages).hasSize(2);
        assertThat(messages.get(1).getText()).isEqualTo("Hello Emre 2");
    }
    
    @Test
    void test_Update_Message(){
        Message oldMockMessage = new Message();
        oldMockMessage.setId(1);
        oldMockMessage.setText("Hello Emre");
        oldMockMessage.setMessageStatus(MessageStatus.UNREAD);

        Message newMockMessage = new Message();
        newMockMessage.setId(1);
        newMockMessage.setText("Hello New Emre");
        newMockMessage.setMessageStatus(MessageStatus.UNREAD);

        when(messageRepository.findById(1)).thenReturn(Optional.of(oldMockMessage));
        when(messageRepository.save(any(Message.class))).thenReturn(newMockMessage);

        Message updated = messageService.update(newMockMessage);

        assertThat(updated).isNotNull();
        assertThat(updated.getText()).isEqualTo("Hello New Emre");

    }

    @Test
    void test_Delete_Message_By_Id(){
        Message deleteMockMessage = new Message();
        deleteMockMessage.setId(1);
        deleteMockMessage.setText("Hello Emre");
        deleteMockMessage.setMessageStatus(MessageStatus.UNREAD);

        when(messageRepository.findById(1)).thenReturn(Optional.of(deleteMockMessage));
        messageService.deleteById(1);

        verify(messageRepository,times(1)).deleteById(1);

    }

    @Test
    void test_Get_Messages_Receiver_By_User(){
        User sender = new User();
        sender.setUsername("Don Emre");

        User receiver = new User();
        receiver.setUsername("Patron Emre");

        Message mockMessage = new Message();
        mockMessage.setText("This is Don Emre");
        mockMessage.setSender(sender);
        mockMessage.setReceiver(receiver);

        Message mockMessage2 = new Message();
        mockMessage2.setText("This is Don Emre Again");
        mockMessage2.setSender(sender);
        mockMessage2.setReceiver(receiver);

        Set<Message> messageSet = new HashSet<>(Arrays.asList(mockMessage,mockMessage2));

        when(messageRepository.findByReceiverId(receiver.getId())).thenReturn(messageSet);

        Set<Message> messages =  messageService.getMessagesReceiverByUser(receiver.getId());

        assertThat(messages).hasSize(2);
        assertThat(messages).contains(mockMessage,mockMessage2);
    }

    @Test
    void test_Get_Messages_Sender_By_User(){
        User sender = new User();
        sender.setId(1);
        sender.setUsername("Patron Emre");

        User receiver = new User();
        receiver.setId(2);
        receiver.setUsername("Don Emre");

        Message mockMessage = new Message();
        mockMessage.setText("This is Patron Emre, You owe me secret of Java");
        mockMessage.setSender(sender);
        mockMessage.setReceiver(receiver);

        Message mockMessage2 = new Message();
        mockMessage2.setText("This is Patron Emre Again, it will cost you 1000 commits");
        mockMessage2.setSender(sender);
        mockMessage2.setReceiver(receiver);

        Set<Message> messageSet = new HashSet<>(Arrays.asList(mockMessage,mockMessage2));

        when(messageRepository.findBySenderId(sender.getId())).thenReturn(messageSet);

        Set<Message> messages =  messageService.getMessagesSentByUser(sender.getId());

        assertThat(messages).hasSize(2);
        assertThat(messages).contains(mockMessage,mockMessage2);
    }
}
