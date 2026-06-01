package com.jingjunjin.taskManager.service;

import com.jingjunjin.taskManager.dto.request.CreateUserDTO;
import com.jingjunjin.taskManager.dto.request.UpdateUserDTO;
import com.jingjunjin.taskManager.dto.response.UserResponseDTO;
import com.jingjunjin.taskManager.entity.User;
import com.jingjunjin.taskManager.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private static final String USER_NOT_FOUND_MESSAGE = "User not found";
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));
    }

    private UserResponseDTO mapToDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public UserResponseDTO createUser(CreateUserDTO dto) {
        User user = new User();

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        User savedUser = userRepository.save(user);

        return mapToDTO(savedUser);
    }

    public void deleteUserById(Long id) {
        User user = getUserEntityById(id);
        userRepository.delete(user);
        log.info("User with id {} deleted", id);
    }

    public UserResponseDTO findById(Long id) {
        User user = getUserEntityById(id);

        return mapToDTO(user);
    }

    public UserResponseDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

        return mapToDTO(user);
    }

    public UserResponseDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

        return mapToDTO(user);
    }

    public UserResponseDTO updateUser(Long id, UpdateUserDTO dto) {

        User user = getUserEntityById(id);

        if (dto.getUsername() != null) {
            user.setUsername(dto.getUsername());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null) {
            user.setPassword(dto.getPassword());
        }

        User updated = userRepository.save(user);

        return mapToDTO(updated);
    }
}
