package com.jingjunjin.taskManager.service;

import com.jingjunjin.taskManager.dto.request.CreateTaskDTO;
import com.jingjunjin.taskManager.dto.request.UpdateTaskDTO;
import com.jingjunjin.taskManager.dto.response.TaskResponseDTO;
import com.jingjunjin.taskManager.entity.Task;
import com.jingjunjin.taskManager.entity.User;
import com.jingjunjin.taskManager.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private static final String TASK_NOT_FOUND_MESSAGE = "Task not found";
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }
    
    public Task getTaskEntityById (Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(TASK_NOT_FOUND_MESSAGE));
    }

    private TaskResponseDTO mapToDTO (Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getUser().getId()
        );
    }

    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public TaskResponseDTO createTask (CreateTaskDTO dto) {
        User user = userService.getUserEntityById(dto.getUserId());
        Task task = new Task();

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        task.setDueDate(dto.getDueDate());
        task.setUser(user);

        Task savedTask = taskRepository.save(task);

        return mapToDTO(savedTask);
    }

    public void deleteTaskById (Long id) {
        Task task = getTaskEntityById(id);
        taskRepository.delete(task);
        log.info("Task with id {} deleted", id);
    }

    public TaskResponseDTO findById (Long id) {
        Task task = getTaskEntityById(id);
        
        return mapToDTO(task);
    }

    public TaskResponseDTO updateTask (Long id, @org.jetbrains.annotations.UnknownNullability UpdateTaskDTO dto) {
        Task task = getTaskEntityById(id);

        if (dto.getTitle() != null) {
            task.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            task.setDescription(dto.getDescription());
        }
        if (dto.getStatus() != null) {
            task.setStatus(dto.getStatus());
        }
        if (dto.getPriority() != null) {
            task.setPriority(dto.getPriority());
        }
        if (dto.getDueDate() != null) {
            task.setDueDate(dto.getDueDate());
        }
        if (dto.getUserId() != null) {
            task.setUser(userService.getUserEntityById(dto.getUserId()));
        }

        Task updatedTask = taskRepository.save(task);

        return mapToDTO(updatedTask);

    }

    public List<TaskResponseDTO> getTasksByUserId (Long userId) {

        User user = userService.getUserEntityById(userId);

        return taskRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToDTO)
                .toList();


    }

}
