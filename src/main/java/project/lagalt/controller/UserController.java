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
@CrossOrigin
@RequestMapping(path = "api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @GetMapping
    public ResponseEntity<Collection<UserDTO>> getAllProject(){
        return ResponseEntity.ok(userMapper.usersToUsersDTO(userService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getProjectById(@PathVariable int id){
        User user = userService.findById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }

        return ResponseEntity.ok(userMapper.userToUserDTO(user));
    }

    @PostMapping
    public ResponseEntity<User> addProject(@RequestBody UserPostDTO userPostDTO){
        return ResponseEntity.ok(userService.add(userMapper.userPostToUser(userPostDTO)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateProject(@RequestBody UserUpdateDTO userUpdateDTO, @PathVariable int id){

        if (userService.findById(id) == null) {
            throw new UserNotFoundException(id);
        }
        userUpdateDTO.setId(id);
        return ResponseEntity.ok(userService.update(userMapper.userUpdateToUser(userUpdateDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteProject(@PathVariable int id){
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
