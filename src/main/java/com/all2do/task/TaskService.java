package com.all2do.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Iterable<Task> getAll() {
        return taskRepository.findAll();
    }

    public void create(CreateTaskDto createTaskDto) {
        Task task = new Task();
        task.setContent(createTaskDto.getContent());
        Date date = new Date();
        task.setCreationDate(date);
        taskRepository.save(task);
    }

    public void delete(DeleteTaskDto deleteTaskDto) {
        taskRepository.deleteById(deleteTaskDto.getId());
    }
}
