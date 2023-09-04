package project.lagalt.service;

import project.lagalt.model.entities.User;

public interface UserService extends CrudService<User, Integer> {
    public void createUserFromToken(String token);

    public User findByUsername(String username);
}
