package com.example.simpleProject.service;

import com.example.simpleProject.dao.UserRepository;
import com.example.simpleProject.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean saveUser(User user){
        try {
            userRepository.save(user);
            return true; // Save successful
        } catch (Exception e) {
            return false; // Save failed
        }
    }

    public User findByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found with username: " + email);
        }
        return user;
    }

    public boolean deleteUserByEmail(int id) {
        try {
            userRepository.deleteById(id);
            return true; // Deleted successful
        } catch (Exception e) {
            return false; // Deleted failed
        }
    }

}
