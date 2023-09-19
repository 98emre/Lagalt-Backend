package project.lagalt.serviceImpl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.stereotype.Service;

import project.lagalt.model.entities.User;
import project.lagalt.repository.UserRepository;
import project.lagalt.service.UserService;
import project.lagalt.utilites.enums.ProfileVisibility;
import project.lagalt.utilites.enums.Skills;
import project.lagalt.utilites.exceptions.user.UserAlreadyExistsException;
import project.lagalt.utilites.exceptions.user.UserNotFoundException;

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
    public User add(User user) {

        if(userRepository.existsByUsername(user.getUsername()) || userRepository.existsByUsername(user.getEmail())){
            throw new UserAlreadyExistsException(user.getUsername());
        }

        return userRepository.save(user);
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

        if(user.getDescription() != null){
            updatedUser.setDescription(user.getDescription());
        }

        if(user.getProfileVisibility() != null){
            updatedUser.setProfileVisibility(user.getProfileVisibility());
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
        String email = jwt.getClaim("email");
        String fullname = jwt.getClaim("name");

        if(userRepository.existsByUsername(username)){
            throw new UserAlreadyExistsException(username);
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setFullname(fullname);
        user.setProfileVisibility(ProfileVisibility.PUBLIC);


        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    public User findByToken(String username) {
        Jwt jwt = JwtDecoders.fromIssuerLocation("https://lemur-8.cloud-iam.com/auth/realms/case-lagalt").decode(username);

        String name = jwt.getClaim("preferred_username");

        return userRepository.findByUsername(name).orElseThrow(() -> new UserNotFoundException(name));
    }

    @Override
    public Collection<User> findAllByName(String userName, String fullName) {
        return userRepository.findAllByUsernameIgnoreCaseContainingOrFullnameIgnoreCaseContaining(userName, fullName);
    }
}
