package project.lagalt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.lagalt.model.User;
import project.lagalt.service.UserService;
import project.lagalt.utilites.exceptions.UserNotFoundException;

import java.util.Collection;

@RestController
@RequestMapping(path = "api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<Collection<User>> getAllUser(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id){
        User user = userService.findById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user){
        return ResponseEntity.ok(userService.add(user));
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user){
        user.setId(id);

        if (userService.findById(id) == null) {
            throw new UserNotFoundException(id);
        }

        return ResponseEntity.ok(user);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
