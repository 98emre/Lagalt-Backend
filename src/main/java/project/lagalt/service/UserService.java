package project.lagalt.service;

import project.lagalt.model.entities.User;

import java.util.Set;

public interface UserService extends CrudService<User, Integer> {
    public void createUserFromToken(String token);

    public User findByUsername(String username);

    public User findByToken(String username);

}
