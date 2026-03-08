package com.example.secure_task_ui.repository;

import com.example.secure_task_ui.entity.TaskEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        return em.find(TaskEntity.class, id);
    }

    @Transactional
    public TaskEntity save(TaskEntity task) {
        if (task.getId() == null) {
            em.persist(task);   // INSERT
            return task;
        } else {
            return em.merge(task); // UPDATE
        }
    }

    @Transactional
    public void deleteById(Long id) {
        TaskEntity entity = em.find(TaskEntity.class, id);
        if (entity != null) {
            em.remove(entity);
        }
    }
}
