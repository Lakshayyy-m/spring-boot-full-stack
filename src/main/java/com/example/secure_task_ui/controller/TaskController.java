package com.example.secure_task_ui.controller;

import com.example.secure_task_ui.entity.TaskEntity;
import com.example.secure_task_ui.repository.TaskRepository;
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
        repo.save(task);
        return "redirect:/tasks";
    }

    // Show edit page for a specific task
    @GetMapping("/{id}/edit")
    public String editTask(@PathVariable("id") Long id, Model model) {
        model.addAttribute("taskForm", repo.findById(id));
        model.addAttribute("tasks", repo.findAll());
        return "tasks";
    }

    // Handle update of an existing task
    @PostMapping("/{id}/update")
    public String updateTask(@PathVariable("id") Long id,
                             @ModelAttribute("taskForm") TaskEntity formTask) {
        TaskEntity existing = repo.findById(id);
        existing.setTitle(formTask.getTitle());
        existing.setDescription(formTask.getDescription());
        existing.setCompleted(formTask.isCompleted());
        repo.save(existing);
        return "redirect:/tasks";
    }

    // Handle delete
    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable("id") Long id) {
        repo.deleteById(id);
        return "redirect:/tasks";
    }
}
