package com.example.simpleProject.service;

import com.example.simpleProject.dao.UserRepository;
import com.example.simpleProject.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser_Success() {
        User user = new User("John", "john@example.com");

        when(userRepository.save(any(User.class))).thenReturn(user);

        boolean isSaved = userService.saveUser(user);

        assertTrue(isSaved);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testSaveUser_Failure() {
        User user = new User("John", "john@example.com");

        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Save failed"));

        boolean isSaved = userService.saveUser(user);

        assertFalse(isSaved);
    }

    @Test
    void testFindByEmail_UserFound() throws Exception {
        User user = new User("John", "john@example.com");

        when(userRepository.findByEmail("john@example.com")).thenReturn(user);

        User foundUser = userService.findByEmail("john@example.com");

        assertEquals(user, foundUser);
    }

    @Test
    void testFindByEmail_UserNotFound() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> {
            userService.findByEmail("john@example.com");
        });

        assertEquals("User not found with username: john@example.com", exception.getMessage());
    }

    @Test
    void testDeleteUserByEmail_Success() {
        User user = new User("John", "john@example.com");
        user.setId(1);

        when(userRepository.findByEmail("john@example.com")).thenReturn(user);
        doNothing().when(userRepository).deleteById(anyInt());

        boolean isDeleted = userService.deleteUserByEmail(1);

        assertTrue(isDeleted);
    }

    @Test
    void testDeleteUserByEmail_Failure() {
        User user = new User("John", "john@example.com");
        user.setId(1);

        when(userRepository.findByEmail("john@example.com")).thenReturn(user);
        doThrow(new EmptyResultDataAccessException(1)).when(userRepository).deleteById(anyInt());

        boolean isDeleted = userService.deleteUserByEmail(1);

        assertTrue(!isDeleted);
    }
}
