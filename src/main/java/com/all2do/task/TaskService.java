package com.all2do.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public Response<Task> getPage(int currentPage) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest page = PageRequest.of(currentPage, 10, sort);
        Page<Task> tasksPage = taskRepository.findAll(page);

        return new Response<>(tasksPage.getNumber(), tasksPage.getTotalPages(), tasksPage.getTotalElements(), tasksPage.getContent());
    }

    public Response<Task> getPage(int currentPage, String search) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest page = PageRequest.of(currentPage, 10, sort);
        Page<Task> tasksPage = taskRepository.findByContentContaining(search, page);

        return new Response<>(tasksPage.getNumber(), tasksPage.getTotalPages(), tasksPage.getTotalElements(), tasksPage.getContent());
    }

    public Task create(CreateTaskDto createTaskDto) {
        Task task = new Task();
        task.setContent(createTaskDto.getContent());
        Date date = new Date();
        task.setCreationDate(date);
        return taskRepository.save(task);
    }

    public void delete(IdTaskDto idTaskDto) {
        taskRepository.deleteById(idTaskDto.getId());
    }

    public void updateStatus(IdTaskDto idTaskDto) {
        Task task = taskRepository.findById(idTaskDto.getId()).get();
        task.setCompleted(!task.isCompleted());
        taskRepository.save(task);
    }
}
