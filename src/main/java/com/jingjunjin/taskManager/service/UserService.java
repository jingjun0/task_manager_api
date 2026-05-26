package com.jingjunjin.taskManager.service;

import com.jingjunjin.taskManager.entity.User;
import com.jingjunjin.taskManager.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final String USER_NOT_FOUND_MESSAGE = "User not found";

    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser (User user) {
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
            User user = findById(id);
            userRepository.delete(user);
            System.out.println("User by id: " +id+ " deleted");
    }

    public User findById (Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));
    }

    public User findByEmail (String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));
    }

    public User findByUsername (String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));
    }

    public User updateUser (Long id, User newData) {
        User existingUser = findById(id);

        if (newData.getUsername() != null) {
            existingUser.setUsername(newData.getUsername());
        }
        if (newData.getEmail() != null) {
            existingUser.setEmail(newData.getEmail());
        }
        if (newData.getPassword() != null) {
            existingUser.setPassword(newData.getPassword());
        }

        return userRepository.save(existingUser);
    }

}
