package com.taskmanager.taskmanager.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Data
@Table(name = "tasks")
public class Task {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private String status; // Pending, In Progress, Completed

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Link to User entity

    // Transient field for userId in the JSON payload
    @Transient
    private Long userId;

    // Ensure the user is set before saving the task
    @PrePersist
    public void prePersist() {
        if (this.user == null && this.userId != null) {
            User user = new User();
            user.setId(this.userId);
            this.user = user;
        }
    }

    public Long getUserId() {
        return this.user != null ? this.user.getId() : null;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
        if (userId != null && this.user == null) {
            User user = new User();
            user.setId(userId);
            this.user = user;
        }
    }
}