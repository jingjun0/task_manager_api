package com.jingjunjin.taskManager.controller;


import com.jingjunjin.taskManager.entity.User;
import com.jingjunjin.taskManager.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers () {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById (@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail (@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/username/{username}")
    public User getUserByUsername (@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @PostMapping
    public User createUser (@RequestBody User user) {
        return userService.createUser(user);
    }

    @DeleteMapping("{id}")
    public void deleteUser (@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @PutMapping("{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }



}
