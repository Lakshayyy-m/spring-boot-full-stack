package com.example.secure_task_ui.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;      // Primary key, auto-increment

    @Column(nullable = false)
    private String title; // Short task title

    @Column(nullable = false)
    private String description; // Longer description

    @Column(nullable = false)
    private boolean completed;  // Completed flag

    public TaskEntity() {
        // Required by JPA
    }

    public TaskEntity(String title, String description, boolean completed) {
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
