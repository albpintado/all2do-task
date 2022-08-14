package com.all2do.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping(path="all")
    public ResponseEntity<Iterable<Task>> getAllTasks() {
        Iterable<Task> tasks = taskService.getAll();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping(path="create")
    public ResponseEntity<String> createTask(@RequestBody CreateTaskDto createTaskDto) {
        taskService.create(createTaskDto);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

    @DeleteMapping(path="delete")
    public ResponseEntity<String> deleteTask(@RequestBody IdTaskDto idTaskDto) {
        taskService.delete(idTaskDto);
        return new ResponseEntity<>("Deleted", HttpStatus.NO_CONTENT);
    }

    @PutMapping(path="update")
    public ResponseEntity<String> updateStatus(@RequestBody IdTaskDto idTaskDto) {
        taskService.updateStatus(idTaskDto);
        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }
}
