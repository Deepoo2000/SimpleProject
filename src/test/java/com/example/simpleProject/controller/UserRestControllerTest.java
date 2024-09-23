package com.example.simpleProject.controller;

import com.example.simpleProject.dto.UserRequest;
import com.example.simpleProject.entity.User;
import com.example.simpleProject.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserRestControllerTest {

    @InjectMocks
    private UserRestController userRestController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser_Success() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("John");
        userRequest.setEmail("john@example.com");

        when(userService.saveUser(any(User.class))).thenReturn(true);

        ResponseEntity<String> response = userRestController.saveUser(userRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals("User Saved Successfully", response.getBody());
    }

    @Test
    void testSaveUser_Failure() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("John");
        userRequest.setEmail("john@example.com");

        when(userService.saveUser(any(User.class))).thenReturn(false);

        ResponseEntity<String> response = userRestController.saveUser(userRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to save user", response.getBody());
    }

    @Test
    void testFindByEmail_UserFound() throws Exception {
        User user = new User("John", "john@example.com");
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("john@example.com");

        when(userService.findByEmail("john@example.com")).thenReturn(user);

        ResponseEntity<?> response = userRestController.findByEmail(userRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testFindByEmail_UserNotFound() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("john@example.com");

        when(userService.findByEmail("john@example.com")).thenReturn(null);

        ResponseEntity<?> response = userRestController.findByEmail(userRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User Not Found", response.getBody());
    }

    @Test
    void testDeleteUser_UserDeleted() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("john@example.com");

        User user = new User("John", "john@example.com");
        user.setId(1);

        when(userService.findByEmail("john@example.com")).thenReturn(user);
        when(userService.deleteUserByEmail(1)).thenReturn(true);

        ResponseEntity<?> response = userRestController.deleteUserByEmail(userRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User Deleted", response.getBody());
    }

    @Test
    void testDeleteUser_UserNotFound() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("john@example.com");

        when(userService.findByEmail("john@example.com")).thenReturn(null);

        ResponseEntity<?> response = userRestController.deleteUserByEmail(userRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User Not Found", response.getBody());
    }

    @Test
    void testDeleteUser_DeleteFailed() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("john@example.com");

        User user = new User("John", "john@example.com");
        user.setId(1);

        when(userService.findByEmail("john@example.com")).thenReturn(user);
        when(userService.deleteUserByEmail(1)).thenReturn(false);

        ResponseEntity<?> response = userRestController.deleteUserByEmail(userRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to delete user", response.getBody());
    }
}
