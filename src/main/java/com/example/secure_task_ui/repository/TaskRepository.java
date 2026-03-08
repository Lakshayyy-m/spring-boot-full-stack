package com.example.securetaskui.repository;

import com.example.securetaskui.entity.TaskEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepository {

    @PersistenceContext
    private EntityManager em;

    public List<TaskEntity> findAll() {
        return em.createQuery("SELECT t FROM TaskEntity t", TaskEntity.class)
                 .getResultList();
    }

    public TaskEntity findById(Long id) {
        // TODO A: Use EntityManager to find a single TaskEntity by its primary key.
        // Hint: Use em.find(TaskEntity.class, id).
        return null;
    }

    public TaskEntity save(TaskEntity task) {
        if (task.getId() == null) {
            em.persist(task);   // INSERT
            return task;
        } else {
            return em.merge(task); // UPDATE
        }
    }

    public void deleteById(Long id) {
        // TODO B: Delete a task by id.
        // Hint: First find the entity, then call em.remove(entity) if it's not null.
    }
}
