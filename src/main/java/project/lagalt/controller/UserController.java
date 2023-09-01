package project.lagalt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.lagalt.mapper.UserMapper;
import project.lagalt.model.dtos.user.UserDTO;
import project.lagalt.model.dtos.user.UserPostDTO;
import project.lagalt.model.dtos.user.UserUpdateDTO;
import project.lagalt.model.entities.User;
import project.lagalt.service.UserService;
import project.lagalt.utilites.exceptions.UserNotFoundException;

import java.util.Collection;

@RestController
@RequestMapping(path = "api/users")
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @GetMapping
    public ResponseEntity<Collection<UserDTO>> getAllUser(){
        return ResponseEntity.ok(userMapper.usersToUsersDTO(userService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id){
        User user = userService.findById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }

        return ResponseEntity.ok(userMapper.userToUserDTO(user));
    }

    @PostMapping("/add-user-dto")
    public ResponseEntity<User> addUser(@RequestBody UserPostDTO userPostDTO){
        return ResponseEntity.ok(userService.add(userMapper.userPostToUser(userPostDTO)));
    }

    @PostMapping("/add-user-token")
    public ResponseEntity<?> addUserFromToken(@RequestHeader("Authorization") String bearerToken){
        String token = bearerToken.replace("Bearer ", "");
        System.out.println("Received Token: " + token);

        userService.createUserFromToken(token);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody UserUpdateDTO userUpdateDTO, @PathVariable int id){

        if (userService.findById(id) == null) {
            throw new UserNotFoundException(id);
        }
        userUpdateDTO.setId(id);
        return ResponseEntity.ok(userService.update(userMapper.userUpdateToUser(userUpdateDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id){
        User deletedUser = userService.findById(id);

        if (deletedUser == null) {
            throw new UserNotFoundException(id);
        }

        userService.deleteById(id);

        return ResponseEntity.status(200).build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
