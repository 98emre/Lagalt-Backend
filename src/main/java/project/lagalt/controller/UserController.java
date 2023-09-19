package project.lagalt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import project.lagalt.mapper.UserMapper;
import project.lagalt.model.dtos.user.UserDTO;
import project.lagalt.model.dtos.user.UserUpdateDTO;
import project.lagalt.model.entities.User;
import project.lagalt.service.UserService;
import project.lagalt.utilites.enums.ProfileVisibility;
import project.lagalt.utilites.exceptions.UserAlreadyExistsException;
import project.lagalt.utilites.exceptions.UserNotFoundException;

import java.net.URI;
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
    public ResponseEntity<UserDTO> getUserById(@PathVariable(value = "id") int id){
        User user = userService.findById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }

        UserDTO userDTO = userMapper.userToUserDTO(user);


        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("public/search")
    public ResponseEntity<Collection<UserDTO>> findByName(@RequestParam(value = "name") String name){
        return ResponseEntity.ok(userMapper.usersToUsersDTO(userService.findAllByName(name, name)));
    }

    @GetMapping("public/username/{username}")
    public ResponseEntity<UserDTO> getUserByName(@PathVariable(value = "username") String username){
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        UserDTO userDTO = userMapper.userToUserDTO(user);


        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("public/token/username")
    public ResponseEntity<UserDTO> getUserByToken(@RequestHeader("Authorization") String bearerToken){
        String token = bearerToken.replace("Bearer ", "");
        User user = userService.findByToken(token);

        if (user == null) {
            throw new UserNotFoundException("Token");
        }

        UserDTO userDTO = userMapper.userToUserDTO(user);
        return ResponseEntity.ok(userDTO);
    }
    
    @PostMapping("/add-user")
    public ResponseEntity<?> addUserFromToken(@RequestHeader("Authorization") String bearerToken){
        String token = bearerToken.replace("Bearer ", "");

        userService.createUserFromToken(token);

        User user = userService.findByToken(token);
        UserDTO userDTO = userMapper.userToUserDTO(user);

        URI location = URI.create("/api/users/public/" + user.getId());

        return ResponseEntity.created(location).body(userDTO);
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserUpdateDTO userUpdateDTO, @PathVariable(value = "id") int id){

        if (userService.findById(id) == null) {
            throw new UserNotFoundException(id);
        }
        userUpdateDTO.setId(id);
        User user = userService.update(userMapper.userUpdateToUser(userUpdateDTO));

        UserDTO userDTO = userMapper.userToUserDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<User> deleteUser(@PathVariable(value = "id") int id){
        User deletedUser = userService.findById(id);

        if (deletedUser == null) {
            throw new UserNotFoundException(id);
        }

        userService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllOtherExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
}
