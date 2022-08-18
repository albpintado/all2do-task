package com.all2do.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping(path = "all")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAll();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping(path = "get")
    public ResponseEntity<Map<String, Object>> getTasksInPage(@RequestParam(required = false) String search,
                                                         @RequestParam(defaultValue = "0") int page) {
        Page<Task> tasksPage;

        try {
            if (search == null) {
                tasksPage = taskService.getPage(page);
            }

            else {
                tasksPage = taskService.getPage(page, search);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("tasks", tasksPage.getContent());
            response.put("currentPage", tasksPage.getNumber());
            response.put("totalPages", tasksPage.getTotalPages());
            response.put("totalTasks", tasksPage.getTotalElements());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "create")
    public ResponseEntity<Task> createTask(@RequestBody CreateTaskDto createTaskDto) {
        Task task = taskService.create(createTaskDto);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "delete")
    public ResponseEntity<String> deleteTask(@RequestBody IdTaskDto idTaskDto) {
        taskService.delete(idTaskDto);
        return new ResponseEntity<>("Deleted", HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "update")
    public ResponseEntity<String> updateStatus(@RequestBody IdTaskDto idTaskDto) {
        taskService.updateStatus(idTaskDto);
        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }
}
