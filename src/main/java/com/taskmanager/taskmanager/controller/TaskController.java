package com.taskmanager.taskmanager.controller;




import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired

    private TaskService taskService;


    @PostMapping


    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        List<Task>allTask =  taskService.getAllTasks();
        return allTask;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task taskDetails) {
        Task updatedTask = taskService.updateTask(id, taskDetails);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        String s = taskService.deleteTask(id);

        return ResponseEntity.ok(s);
    }


    @GetMapping("/status")
    public ResponseEntity<List<Task>> getAllTasksByStatus(@RequestParam(required = false) String status) {
        List<Task> tasks;
        if (status != null) {
            // Filter tasks by status if the status parameter is provided
            tasks = taskService.getTasksByStatus(status);
        } else {
            // Otherwise, return all tasks
            tasks = taskService.getAllTasks();
        }
        return ResponseEntity.ok(tasks);
    }


    @GetMapping("/paging")
    public ResponseEntity<Page<Task>> getAllTasksByPaging(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Page<Task> tasks;
        if (status != null) {
            // Fetch tasks filtered by status with pagination
            tasks = taskService.getTasksByStatus(status, page, size, sortBy, sortDir);
        } else {
            // Fetch all tasks with pagination
            tasks = taskService.getAllTasks(page, size, sortBy, sortDir);
        }
        return ResponseEntity.ok(tasks);
    }
}