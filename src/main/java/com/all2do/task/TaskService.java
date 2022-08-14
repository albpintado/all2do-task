package com.all2do.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

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

    public void delete(IdTaskDto idTaskDto) {
        taskRepository.deleteById(idTaskDto.getId());
    }

    public void updateStatus(IdTaskDto idTaskDto) {
        Task task = taskRepository.getReferenceById(idTaskDto.getId());
        task.setCompleted(!task.isCompleted());
        taskRepository.save(task);
    }
}
