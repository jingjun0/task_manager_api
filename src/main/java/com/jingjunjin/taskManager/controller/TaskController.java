package com.jingjunjin.taskManager.controller;


import com.jingjunjin.taskManager.dto.request.CreateTaskDTO;
import com.jingjunjin.taskManager.dto.request.UpdateTaskDTO;
import com.jingjunjin.taskManager.dto.response.TaskResponseDTO;
import com.jingjunjin.taskManager.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController (TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskResponseDTO> getAllTasks () {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public TaskResponseDTO getTaskById (@PathVariable Long id) {
        return taskService.findById(id);
    }

    @GetMapping("/user/{userId}")
    public List<TaskResponseDTO> getTasksByUserId (@PathVariable Long userId) {
        return taskService.getTasksByUserId(userId);
    }

    @PostMapping
    public TaskResponseDTO createTask (@RequestBody CreateTaskDTO dto) {
        return taskService.createTask(dto);
    }

    @DeleteMapping("{id}")
    public void deleteTask (@PathVariable Long id) {
        taskService.deleteTaskById(id);
    }

    @PutMapping("{id}")
    public TaskResponseDTO updateTask (@PathVariable Long id, @RequestBody UpdateTaskDTO dto) {
        return taskService.updateTask(id, dto);
    }

}

