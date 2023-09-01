package project.lagalt.service;

import project.lagalt.model.entities.User;

import java.util.Collection;

public interface UserService extends CrudService<User, Integer> {

    Collection<User> findAllByName(String name);
}
