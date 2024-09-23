package com.example.simpleProject.controller;

import com.example.simpleProject.dto.UserRequest;
import com.example.simpleProject.entity.User;
import com.example.simpleProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/saveUser")
    public ResponseEntity<String> saveUser(@RequestBody UserRequest userRequest){
        User user = new User(userRequest.getName(), userRequest.getEmail());

        boolean isSaved = userService.saveUser(user);

        if (isSaved) {
            return ResponseEntity.ok("User Saved Successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to save user");
        }
    }

    @GetMapping("/findUser")
    public ResponseEntity<?> findByEmail(@RequestBody UserRequest userRequest) throws Exception {
        if(userService.findByEmail(userRequest.getEmail()) != null)
            return ResponseEntity.ok(userService.findByEmail(userRequest.getEmail()));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<?>  deleteUserByEmail(@RequestBody UserRequest userRequest) throws Exception {
        User user = userService.findByEmail(userRequest.getEmail());
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        }

        boolean isDeleted = userService.deleteUserByEmail(user.getId());

        if (isDeleted) {
            return ResponseEntity.ok("User Deleted");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete user");
        }
    }

}
