package com.jingjunjin.taskManager.repository;

import com.jingjunjin.taskManager.entity.Task;
import com.jingjunjin.taskManager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepository extends JpaRepository <Task, Long> {


}
