package project.lagalt.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.lagalt.model.User;
import project.lagalt.repository.UserRepository;
import project.lagalt.service.UserService;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Collection<User> findAll() {
        return null;
    }

    @Override
    public User findById(Integer integer) {
        return null;
    }

    @Override
    public User add(User entity) {
        return null;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public User deleteById(Integer integer) {
        return null;
    }
}
