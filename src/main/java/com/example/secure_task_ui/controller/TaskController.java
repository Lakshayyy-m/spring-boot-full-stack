package com.example.securetaskui.controller;

import com.example.securetaskui.entity.TaskEntity;
import com.example.securetaskui.repository.TaskRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository repo;

    public TaskController(TaskRepository repo) {
        this.repo = repo;
    }

    // Show list of tasks and the form to add a new one
    @GetMapping
    public String listTasks(Model model) {
        model.addAttribute("tasks", repo.findAll());
        model.addAttribute("taskForm", new TaskEntity());
        return "tasks"; // templates/tasks.html
    }

    // Handle form submission for creating a new task
    @PostMapping
    public String createTask(@ModelAttribute("taskForm") TaskEntity task) {
        // For a new task, completed flag is usually false by default,
        // but we accept whatever the form sends.
        repo.save(task);
        return "redirect:/tasks";
    }

    // Show edit page for a specific task
    @GetMapping("/{id}/edit")
    public String editTask(@PathVariable("id") Long id, Model model) {
        // TODO C: Load the task by id from the repository and add it to the model.
        // Hint: Call repo.findById(id), then add it as "taskForm" so we can reuse the same form.
        // Also, add "tasks" to the model so the list is still visible.
        return "tasks";
    }

    // Handle update of an existing task
    @PostMapping("/{id}/update")
    public String updateTask(@PathVariable("id") Long id,
                             @ModelAttribute("taskForm") TaskEntity formTask) {
        // TODO D:
        // 1. Load the existing task from the database using the id.
        // 2. Copy fields from formTask into the existing entity (title, description, completed).
        // 3. Save the existing entity using repo.save(...).
        return "redirect:/tasks";
    }

    // Handle delete
    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable("id") Long id) {
        // TODO E: Call the repository method that deletes a task by id.
        return "redirect:/tasks";
    }
}
