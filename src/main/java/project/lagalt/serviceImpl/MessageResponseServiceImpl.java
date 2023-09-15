package project.lagalt.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.lagalt.model.entities.MessageResponse;
import project.lagalt.repository.MessageResponseRepository;
import project.lagalt.service.MessageResponseService;

import java.util.Collection;

@Service
public class MessageResponseServiceImpl implements MessageResponseService {

    private final MessageResponseRepository messageResponseRepository;

    @Autowired
    public MessageResponseServiceImpl(MessageResponseRepository messageResponseRepository) {
        this.messageResponseRepository = messageResponseRepository;
    }

    @Override
    public Collection<MessageResponse> findAll() {
        return messageResponseRepository.findAll();
    }

    @Override
    public MessageResponse findById(Integer integer) {
        return messageResponseRepository.findById(integer).orElse(null);
    }

    @Override
    public MessageResponse add(MessageResponse entity) {
        return messageResponseRepository.save(entity);
    }

    @Override
    public MessageResponse update(MessageResponse entity) {
        return messageResponseRepository.save(entity);
    }

    @Override
    public void deleteById(Integer integer) {
        messageResponseRepository.deleteById(integer);
    }
}
