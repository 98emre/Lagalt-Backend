package project.lagalt.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.lagalt.model.entities.Message;
import project.lagalt.repository.MessageRepository;
import project.lagalt.service.MessageService;
import project.lagalt.utilites.exceptions.MessageNotFoundException;

import java.util.Collection;
import java.util.Set;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Collection<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Message findById(Integer id) {
        return messageRepository.findById(id).orElseThrow(() -> new MessageNotFoundException(id));
    }

    @Override
    public Message add(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Message update(Message message) {
        Message messageUpdated  = messageRepository.findById(message.getId()).orElseThrow(() -> new MessageNotFoundException(message.getId()));


        if(message.getMessageStatus() !=null){
            messageUpdated.setMessageStatus(message.getMessageStatus());
        }

        if(message.getDate() != null){
            messageUpdated.setDate(message.getDate());
        }

        if(message.getReceiver() != null){
            messageUpdated.setReceiver(message.getReceiver());
        }

        if(message.getText() != null){
            messageUpdated.setText(message.getText());
        }

        if(message.getSender() != null){
            messageUpdated.setSender(message.getSender());
        }

        if(message.getTitle() != null){
            messageUpdated.setTitle(message.getTitle());
        }

        return messageRepository.save(messageUpdated);
    }

    @Override
    public void deleteById(Integer id) {
        messageRepository.findById(id).orElseThrow(() -> new MessageNotFoundException(id));

        messageRepository.deleteById(id);
    }

    @Override
    public Set<Message> getMessagesSentByUser(int senderId) {
        return messageRepository.findBySenderId(senderId);
    }

    @Override
    public Set<Message> getMessagesReceiverByUser(int receiverId) {
        return messageRepository.findByReceiverId(receiverId);
    }
}
