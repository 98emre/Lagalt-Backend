package project.lagalt.service;

import project.lagalt.model.entities.Message;
import java.util.Set;

public interface MessageService extends CrudService<Message,Integer> {

    public Set<Message> getMessagesSentByUser(int senderId);
    public Set<Message> getMessagesReceiverByUser(int receiverId);

}
