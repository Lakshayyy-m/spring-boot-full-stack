package com.example.secure_task_ui;

import com.example.secure_task_ui.controller.TaskController;
import com.example.secure_task_ui.entity.TaskEntity;
import com.example.secure_task_ui.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskRepository repo;

    @Test
    void listTasks_requiresAuthentication() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void listTasks_returnsTasksView() throws Exception {
        when(repo.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(view().name("tasks"))
                .andExpect(model().attributeExists("tasks"))
                .andExpect(model().attributeExists("taskForm"));
    }

    @Test
    @WithMockUser
    void createTask_savesAndRedirects() throws Exception {
        when(repo.save(any(TaskEntity.class))).thenReturn(new TaskEntity("Title", "Desc", false));

        mockMvc.perform(post("/tasks")
                        .with(csrf())
                        .param("title", "Title")
                        .param("description", "Desc"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tasks"));

        verify(repo).save(any(TaskEntity.class));
    }

    @Test
    @WithMockUser
    void editTask_loadsTaskAndReturnsView() throws Exception {
        TaskEntity task = new TaskEntity("Edit Me", "Desc", false);
        when(repo.findById(1L)).thenReturn(task);
        when(repo.findAll()).thenReturn(List.of(task));

        mockMvc.perform(get("/tasks/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("tasks"))
                .andExpect(model().attributeExists("taskForm"))
                .andExpect(model().attributeExists("tasks"));
    }

    @Test
    @WithMockUser
    void updateTask_updatesAndRedirects() throws Exception {
        TaskEntity existing = new TaskEntity("Old", "OldDesc", false);
        when(repo.findById(1L)).thenReturn(existing);
        when(repo.save(any(TaskEntity.class))).thenReturn(existing);

        mockMvc.perform(post("/tasks/1/update")
                        .with(csrf())
                        .param("title", "New Title")
                        .param("description", "New Desc")
                        .param("completed", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tasks"));

        verify(repo).save(any(TaskEntity.class));
    }

    @Test
    @WithMockUser
    void deleteTask_deletesAndRedirects() throws Exception {
        mockMvc.perform(post("/tasks/1/delete")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tasks"));

        verify(repo).deleteById(1L);
    }
}
