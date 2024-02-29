package fr.porchet.todoBack.controller;

import java.util.ArrayList;

import org.hibernate.mapping.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import fr.porchet.todoBack.entity.Todo;
import fr.porchet.todoBack.repository.TodoRepository;

@RequestMapping("/todo")
@RestController
public class TodoController {

    private TodoRepository todoRepository;
    
    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping
    public Page<Todo> getAll(Pageable pageable) {
        return todoRepository.findAll(pageable);
    }

    @PostMapping
    public void createTodo(@RequestBody Todo todo) {
        todoRepository.save(todo);
    }

    @ExceptionHandler()
    public String handleException(Exception ex, WebRequest request) {
        return "Error";
    }
}
