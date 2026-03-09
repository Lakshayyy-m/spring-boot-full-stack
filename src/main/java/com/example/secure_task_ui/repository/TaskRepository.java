package com.example.secure_task_ui.repository;

import com.example.secure_task_ui.entity.TaskEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TaskRepository {

    @PersistenceContext
    private EntityManager em;

    public List<TaskEntity> findAll() {
        return em
            .createQuery("SELECT t FROM TaskEntity t", TaskEntity.class)
            .getResultList();
    }

    public TaskEntity findById(Long id) {
        // TODO A: Use EntityManager to find a single TaskEntity by its primary key.
        // Hint: Use em.find(TaskEntity.class, id).
        return em.find(TaskEntity.class, id);
    }

    @Transactional
    public TaskEntity save(TaskEntity task) {
        if (task.getId() == null) {
            em.persist(task); // INSERT
            return task;
        } else {
            return em.merge(task); // UPDATE
        }
    }

    @Transactional
    public void deleteById(Long id) {
        // TODO B: Delete a task by id.
        // Hint: First find the entity, then call em.remove(entity) if it's not null.
        TaskEntity task = em.find(TaskEntity.class, id);
        if (task != null) {
            em.remove(task);
        }
    }
}
