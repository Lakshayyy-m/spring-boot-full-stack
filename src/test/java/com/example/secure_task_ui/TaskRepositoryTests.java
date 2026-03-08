package com.example.secure_task_ui;

import com.example.secure_task_ui.entity.TaskEntity;
import com.example.secure_task_ui.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TaskRepository.class)
class TaskRepositoryTests {

    @Autowired
    private TaskRepository repo;

    @Test
    void findAll_returnsEmptyListWhenNoTasks() {
        List<TaskEntity> tasks = repo.findAll();
        assertTrue(tasks.isEmpty());
    }

    @Test
    void save_persistsNewTask() {
        TaskEntity task = new TaskEntity("Test Title", "Test Description", false);
        TaskEntity saved = repo.save(task);

        assertNotNull(saved.getId());
        assertEquals("Test Title", saved.getTitle());
        assertEquals("Test Description", saved.getDescription());
        assertFalse(saved.isCompleted());
    }

    @Test
    void findById_returnsTaskWhenExists() {
        TaskEntity task = new TaskEntity("Find Me", "Description", false);
        TaskEntity saved = repo.save(task);

        TaskEntity found = repo.findById(saved.getId());
        assertNotNull(found);
        assertEquals("Find Me", found.getTitle());
    }

    @Test
    void findById_returnsNullWhenNotExists() {
        TaskEntity found = repo.findById(999L);
        assertNull(found);
    }

    @Test
    void save_updatesExistingTask() {
        TaskEntity task = new TaskEntity("Original", "Desc", false);
        TaskEntity saved = repo.save(task);
        Long id = saved.getId();

        saved.setTitle("Updated");
        saved.setCompleted(true);
        repo.save(saved);

        TaskEntity found = repo.findById(id);
        assertEquals("Updated", found.getTitle());
        assertTrue(found.isCompleted());
    }

    @Test
    void deleteById_removesTask() {
        TaskEntity task = new TaskEntity("To Delete", "Desc", false);
        TaskEntity saved = repo.save(task);
        Long id = saved.getId();

        repo.deleteById(id);

        assertNull(repo.findById(id));
    }

    @Test
    void deleteById_doesNothingWhenIdNotFound() {
        assertDoesNotThrow(() -> repo.deleteById(999L));
    }

    @Test
    void findAll_returnsAllTasks() {
        repo.save(new TaskEntity("Task 1", "Desc 1", false));
        repo.save(new TaskEntity("Task 2", "Desc 2", true));

        List<TaskEntity> tasks = repo.findAll();
        assertEquals(2, tasks.size());
    }
}
