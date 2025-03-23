package com.taskmanager.taskmanager.service;



import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.exception.TaskNotFoundException;
import com.taskmanager.taskmanager.repository.TaskRepository;
import com.taskmanager.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;





@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;


    public Task createTask(Task task) {
        // Fetch the user from the database using the userId
        User user = userRepository.findById(task.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + task.getUserId()));

        // Set the user in the task
        task.setUser(user);

        // Save the task
        return taskRepository.save(task);
    }


    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    }

    public Task updateTask(Long taskId, Task updatedTaskDetails) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));
        task.setTitle(updatedTaskDetails.getTitle());
        task.setDescription(updatedTaskDetails.getDescription());
        task.setStatus(updatedTaskDetails.getStatus());
        return taskRepository.save(task);
    }

    public String deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));
        taskRepository.deleteById(taskId);
        return "Task Deleted Successfully";
    }


    public List<Task> getTasksByStatus(String status) {
        return taskRepository.findByStatus(status);
    }



    public Page<Task> getAllTasks(int page, int size, String sortBy, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return taskRepository.findAll(pageable);
    }

    // Fetch tasks by status with pagination
    public Page<Task> getTasksByStatus(String status, int page, int size, String sortBy, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return taskRepository.findByStatus(status, pageable);
    }



}