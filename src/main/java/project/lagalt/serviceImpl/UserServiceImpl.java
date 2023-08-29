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
        return userRepository.findAll();
    }

    @Override
    public User findById(Integer integer) {
        return userRepository.findById(integer).orElse(null);
    }

    @Override
    public User add(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public User update(User user) {
        User updateUser = userRepository.findById(user.getId()).orElse(null);

        if(user.getUsername() != null){
            updateUser.setUsername(user.getUsername());
        }

        if(user.getPassword() != null){
            updateUser.setPassword(user.getPassword());
        }

        if(user.getSkills() != null){
            updateUser.setSkills(user.getSkills());
        }

        return userRepository.save(updateUser);
    }

    @Override
    public void deleteById(Integer integer) {
        userRepository.findById(integer).orElse(null);

        userRepository.deleteById(integer);
    }
}
