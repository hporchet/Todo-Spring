package fr.porchet.todoBack.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping
    public ResponseEntity<Object> createTodo(@RequestBody Todo todo) {
        if (todo.getDone() == null) {
            todo.setDone(false);
        }
        todoRepository.save(todo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public Optional<Todo> getTodo(@PathVariable("id") long id) {
        return todoRepository.findById(id);
    }
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable("id") long id) {
        todoRepository.deleteById(id);
    }
    
    @PatchMapping
    public ResponseEntity<Object> updateTodo(@RequestBody Todo todo) {
        todoRepository.save(todo);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    // --- crud list ---

    @GetMapping
    public Page<Todo> getAll(@PageableDefault(size = 20) final Pageable pageable) {
        return todoRepository.findAll(pageable);
    }

    @PostMapping("/bulk")
    public ResponseEntity<Object> createTodos(@RequestBody Iterable<Todo> todo) {
        todoRepository.saveAll(todo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ExceptionHandler()
    public Exception handleException(Exception ex, WebRequest request) {
        return ex;
    }
}
