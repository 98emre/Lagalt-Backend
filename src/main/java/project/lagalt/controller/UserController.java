package project.lagalt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import project.lagalt.mapper.UserMapper;
import project.lagalt.model.dtos.user.UserDTO;
import project.lagalt.model.dtos.user.UserUpdateDTO;
import project.lagalt.model.entities.User;
import project.lagalt.service.UserService;
import project.lagalt.utilites.exceptions.UserAlreadyExistsException;
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

    @GetMapping("public")
    public ResponseEntity<Collection<UserDTO>> getAllUser(){
        return ResponseEntity.ok(userMapper.usersToUsersDTO(userService.findAll()));
    }

    @GetMapping("public/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id){
        User user = userService.findById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }

        return ResponseEntity.ok(userMapper.userToUserDTO(user));
    }

    @GetMapping("public/search")
    public ResponseEntity<Collection<UserDTO>> findByName(@RequestParam String name){
        return ResponseEntity.ok(userMapper.usersToUsersDTO(userService.findAllByName(name, name)));
    }

    @GetMapping("public/username/{username}")
    public ResponseEntity<UserDTO> getUserByName(@PathVariable String username){
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        return ResponseEntity.ok(userMapper.userToUserDTO(user));
    }

    @GetMapping("public/token/username")
    public ResponseEntity<UserDTO> getUserByToken(@RequestHeader("Authorization") String bearerToken){
        String token = bearerToken.replace("Bearer ", "");
        User user = userService.findByToken(token);

        if (user == null) {
            throw new UserNotFoundException("Token");
        }

        return ResponseEntity.ok(userMapper.userToUserDTO(user));
    }
    
    @PostMapping("/add-user")
    public ResponseEntity<?> addUserFromToken(@RequestHeader("Authorization") String bearerToken){
        String token = bearerToken.replace("Bearer ", "");
        userService.createUserFromToken(token);

        User user = userService.findByToken(token);

        return ResponseEntity.ok(userMapper.userToUserDTO(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserUpdateDTO userUpdateDTO, @PathVariable int id){

        if (userService.findById(id) == null) {
            throw new UserNotFoundException(id);
        }
        userUpdateDTO.setId(id);
        User user = userService.update(userMapper.userUpdateToUser(userUpdateDTO));
        return ResponseEntity.ok(userMapper.userToUserDTO(user));
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


    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
