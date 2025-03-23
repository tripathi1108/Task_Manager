package com.taskmanager.taskmanager.repository;



import com.taskmanager.taskmanager.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Pagination support for all tasks
   Page<Task> findAll(Pageable pageable);
    Page<Task> findByStatus(String status, Pageable pageable);


    List<Task> findByStatus(String status);
}