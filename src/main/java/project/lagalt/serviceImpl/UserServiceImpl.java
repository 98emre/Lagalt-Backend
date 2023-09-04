package project.lagalt.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.stereotype.Service;
import project.lagalt.model.entities.User;
import project.lagalt.repository.UserRepository;
import project.lagalt.service.UserService;
import project.lagalt.utilites.enums.Skills;
import project.lagalt.utilites.exceptions.UserNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User add(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public User update(User user) {
        User updatedUser = userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException(user.getId()));

        if(user.getUsername() != null){
            updatedUser.setUsername(user.getUsername());
        }

        if(user.getEmail() != null){
            updatedUser.setEmail(user.getEmail());
        }

        if(user.getFullname() != null){
            updatedUser.setEmail(user.getFullname());
        }

        if(user.getSkills() != null){
            Set<Skills> updatedSkills = new HashSet<>();

            for(Skills s: user.getSkills()){
                updatedSkills.add(Skills.valueOf(s.name().toUpperCase()));
            }
            updatedUser.setSkills(updatedSkills);
        }

        return userRepository.save(updatedUser);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        userRepository.deleteById(id);
    }

    @Override
    public void createUserFromToken(String token) {
        Jwt jwt = JwtDecoders.fromIssuerLocation("https://lemur-8.cloud-iam.com/auth/realms/case-lagalt").decode(token);

        String username = jwt.getClaim("preferred_username");
        System.out.println("username " + username);
        User user = new User();
        user.setUsername(username);

        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }
}
