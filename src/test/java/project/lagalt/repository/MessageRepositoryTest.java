package project.lagalt.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.lagalt.model.entities.Message;
import project.lagalt.model.entities.User;
import project.lagalt.utilites.enums.MessageStatus;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void test_Save_Message() {
        Message message = new Message("Test Emre Title", "Test Emre Text", null, MessageStatus.UNREAD);
        message.setSender(null);
        message.setReceiver(null);
        Message savedMessage = messageRepository.save(message);

        assertThat(savedMessage).isNotNull();
        assertThat(savedMessage.getTitle()).isEqualTo("Test Emre Title");
    }

    @Test
    void test_Find_Message_By_Id() {
        Message message = new Message("Test Emre Title", "Test Emre Text", null, MessageStatus.UNREAD);
        Message savedMessage = messageRepository.save(message);

        Optional<Message> foundMessage = messageRepository.findById(savedMessage.getId());

        assertThat(foundMessage).isPresent();
        assertThat(foundMessage.get().getId()).isEqualTo(savedMessage.getId());
    }

    @Test
    void test_Find_All_Messages() {
        Message message1 = new Message("Test Emre Title 1", "Test Emre Text 1", null, MessageStatus.UNREAD);
        messageRepository.save(message1);

        Message message2 = new Message("Test Emre Title 2", "Test Emre Text 2", null, MessageStatus.UNREAD);
        messageRepository.save(message2);

        List<Message> messages = messageRepository.findAll();

        assertThat(messages).hasSize(2);
    }

    @Test
    void test_Delete_Message_By_Id() {
        Message message = new Message("Test Emre Title", "Test Emre Text", null, MessageStatus.UNREAD);
        Message savedMessage = messageRepository.save(message);

        messageRepository.deleteById(savedMessage.getId());

        Optional<Message> foundMessage = messageRepository.findById(savedMessage.getId());

        assertThat(foundMessage).isNotPresent();
    }

    @Test
    void test_Update_Message() {
        Message message = new Message("Test Emre Title", "Test Emre Text", null, MessageStatus.UNREAD);
        Message savedMessage = messageRepository.save(message);

        savedMessage.setTitle("Updated Emre Title");
        messageRepository.save(savedMessage);

        Optional<Message> updatedMessage = messageRepository.findById(savedMessage.getId());

        assertThat(updatedMessage).isPresent();
        assertThat(updatedMessage.get().getTitle()).isEqualTo("Updated Emre Title");
    }

    @Test
    void test_Find_Message_By_Sender_Id() {
        User sender = new User();
        sender.setFullname("Sender Emre Test");
        sender.setEmail("senderemre@gmail.com");
        sender.setUsername("senderemretest");
        User savedSender = userRepository.save(sender);

        Message message = new Message("Test Emre Title", "Test Emre Text", null, MessageStatus.UNREAD);
        message.setSender(savedSender);
        messageRepository.save(message);

        Set<Message> messagesBySender = messageRepository.findBySenderId(savedSender.getId());

        assertThat(messagesBySender).isNotEmpty();
        assertThat(messagesBySender.iterator().next().getSender()).isEqualTo(savedSender);
    }

    @Test
    void test_Find_Message_By_Receiver_Id() {
        User receiver = new User();
        receiver.setFullname("Receiver Emre Test");
        receiver.setEmail("receiveremre@gmail.com");
        receiver.setUsername("receiveremretest");
        User savedReceiver = userRepository.save(receiver);

        Message message = new Message("Test Emer Title", "Test Emre Text", null, MessageStatus.UNREAD);
        message.setReceiver(savedReceiver);
        messageRepository.save(message);

        Set<Message> messagesByReceiver = messageRepository.findByReceiverId(savedReceiver.getId());

        assertThat(messagesByReceiver).isNotEmpty();
        assertThat(messagesByReceiver.iterator().next().getReceiver()).isEqualTo(savedReceiver);
    }
}
