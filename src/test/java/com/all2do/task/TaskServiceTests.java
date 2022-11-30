package com.all2do.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskServiceTests {

    @InjectMocks
    private TaskService taskService;
    private List<Task> list = new ArrayList<>();

    @Mock
    private TaskRepository taskRepository;

    public List<Task> getList() {
        return list;
    }

    public void setList(List<Task> list) {
        this.list = list;
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

        for (int i = 0; i < 25; i++) {
            Task task = new Task();

            task.setId((long) i);
            task.setCompleted(false);
            task.setContent("" + i);

            Calendar calendar = Calendar.getInstance();
            calendar.set(2022, Calendar.AUGUST, 18);
            Date date = calendar.getTime();
            task.setCreationDate(date);

            List<Task> newList = this.getList();
            newList.add(task);
            setList(newList);
        }
    }

    @Test
    public void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(this.list);

        List<Task> response = taskService.getAll();

        verify(taskRepository).findAll();

        assertEquals(25, response.size());
    }

    @Test
    @DisplayName("getPage in page 1 must return 10 elements")
    public void testGetFirstPage() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest page = PageRequest.of(0, 10, sort);

        Page<Task> returnedPage = new PageImpl<>(this.list.subList(0, 10), page, 25);

        when(taskRepository.findAll(page)).thenReturn(returnedPage);

        Response<Task> response = taskService.getPage(0);

        Response<Task> repositoryResponse = new Response<>(0, 3, 25, this.list.subList(0, 10));

        verify(taskRepository).findAll(page);

        assertEquals(repositoryResponse.getElements(), response.getElements());
        assertEquals(repositoryResponse.getElements().size(), response.getElements().size());
    }

    /*@Test
    @DisplayName("getPage in page 3 must return 5 elements")
    public void testGetThirdPage() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest page = PageRequest.of(2, 10, sort);

        Page<Task> returnedPage = new PageImpl<>(new ArrayList<>(this.list.subList(0, 5)));
        when(taskRepository.findAll(page)).thenReturn(returnedPage);

        Map<String, Object> response = taskService.getPage(2);

        verify(taskRepository).findAll(page);

        assertEquals(returnedPage, response);
        assertEquals(5, response.get("totalTasks"));
    }

    @Test
    @DisplayName("getPage in page 3 must return 5 elements")
    public void testGetPageWithFilter() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest page = PageRequest.of(2, 10, sort);
        Page<Task> returnedPage = new PageImpl<>(new ArrayList<>(this.list.subList(0, 10)));

        when(taskRepository.findByContentContaining("betis", page)).thenReturn(returnedPage);

        Map<String, Object> response = taskService.getPage(0, "betis");

        verify(taskRepository).findByContentContaining("betis", page);

        assertEquals(returnedPage, response);
        assertEquals(5, response.get("totalTasks"));
    }*/
}
