package com.taskmanager.taskmanager;



import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.exception.TaskNotFoundException;
import com.taskmanager.taskmanager.repository.TaskRepository;
import com.taskmanager.taskmanager.repository.UserRepository;
import com.taskmanager.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setTimezone("UTC");
        user.setActive(true);

        task = new Task();
        task.setId(1L);
        task.setTitle("Complete Assignment");
        task.setDescription("Finish the Spring Boot assignment");
        task.setStatus("Pending");
        task.setUser(user);
    }

    // Test for creating a task
    @Test
    public void testCreateTask() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user)); // Stub userRepository
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask(task);

        assertNotNull(createdTask);
        assertEquals(task.getTitle(), createdTask.getTitle());
        assertEquals(task.getDescription(), createdTask.getDescription());
        assertEquals(task.getStatus(), createdTask.getStatus());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    // Test for retrieving all tasks
    @Test
    public void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task));

        List<Task> tasks = taskService.getAllTasks();

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        verify(taskRepository, times(1)).findAll();
    }

    // Test for retrieving a task by ID
    @Test
    public void testGetTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task foundTask = taskService.getTaskById(1L);

        assertNotNull(foundTask);
        assertEquals(task.getTitle(), foundTask.getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    // Test for retrieving a task that does not exist
    @Test
    public void testGetTaskById_NotFound() {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(999L));
        verify(taskRepository, times(1)).findById(999L);
    }

    // Test for updating a task
    @Test
    public void testUpdateTask() {
        Task updatedTaskDetails = new Task();
        updatedTaskDetails.setTitle("Updated Title");
        updatedTaskDetails.setDescription("Updated Description");
        updatedTaskDetails.setStatus("In Progress");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task updatedTask = taskService.updateTask(1L, updatedTaskDetails);

        assertNotNull(updatedTask);
        assertEquals(updatedTaskDetails.getTitle(), updatedTask.getTitle());
        assertEquals(updatedTaskDetails.getDescription(), updatedTask.getDescription());
        assertEquals(updatedTaskDetails.getStatus(), updatedTask.getStatus());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    // Test for updating a task that does not exist
    @Test
    public void testUpdateTask_NotFound() {
        Task updatedTaskDetails = new Task();
        updatedTaskDetails.setTitle("Updated Title");

        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(999L, updatedTaskDetails));
        verify(taskRepository, times(1)).findById(999L);
    }

    // Test for deleting a task
    @Test
    public void testDeleteTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }

    // Test for deleting a task that does not exist
    @Test
    public void testDeleteTask_NotFound() {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(999L));
        verify(taskRepository, times(1)).findById(999L);
    }

    // Parameterized test for creating tasks with different users
    @ParameterizedTest
    @MethodSource("provideUsersAndTasks")
    public void testCreateTaskWithDifferentUsers(User user, Task task) {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask(task);

        assertNotNull(createdTask);
        assertEquals(task.getTitle(), createdTask.getTitle());
        assertEquals(task.getDescription(), createdTask.getDescription());
        assertEquals(task.getStatus(), createdTask.getStatus());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    // Method to provide test data for parameterized tests
    static Stream<Arguments> provideUsersAndTasks() {
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setTimezone("UTC");
        user1.setActive(true);

        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Complete Assignment");
        task1.setDescription("Finish the Spring Boot assignment");
        task1.setStatus("Pending");
        task1.setUser(user1); // Set the user for the task

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setTimezone("UTC+1");
        user2.setActive(false);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Write Unit Tests");
        task2.setDescription("Write unit tests for TaskService");
        task2.setStatus("In Progress");
        task2.setUser(user2); // Set the user for the task

        User user3 = new User();
        user3.setId(3L);
        user3.setFirstName("Alice");
        user3.setLastName("Johnson");
        user3.setTimezone("UTC+2");
        user3.setActive(true);

        Task task3 = new Task();
        task3.setId(3L);
        task3.setTitle("Fix Bugs");
        task3.setDescription("Fix bugs in the Task Manager application");
        task3.setStatus("Completed");
        task3.setUser(user3); // Set the user for the task

        return Stream.of(
                Arguments.of(user1, task1),
                Arguments.of(user2, task2),
                Arguments.of(user3, task3)
        );
    }
}