package com.all2do.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public Page<Task> getPage(int currentPage) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest page = PageRequest.of(currentPage, 10, sort);
        return taskRepository.findAll(page);
    }

    public Page<Task> getPage(int currentPage, String search) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest page = PageRequest.of(currentPage, 10, sort);
        return taskRepository.findByContentContaining(search, page);
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
