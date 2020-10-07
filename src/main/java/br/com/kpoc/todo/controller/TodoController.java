package br.com.kpoc.todo.controller;

import br.com.kpoc.todo.controller.dto.TodoDto;
import br.com.kpoc.todo.model.Todo;
import br.com.kpoc.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

@Controller
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody TodoDto todoRequest) {
        Todo todo = new Todo(todoRequest.getTitle(), todoRequest.getDescription());
        try {
            todo = todoService.createTodo(todo);
            return new ResponseEntity<>(todo, HttpStatus.CREATED);
        } catch (HttpClientErrorException ex) {
            return new ResponseEntity<>(ex.getStatusCode());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable("id") UUID id, @RequestBody TodoDto todoRequest) {
        Todo todo = new Todo();
        todo.setId(id);
        todo.setTitle(todoRequest.getTitle());
        todo.setDescription(todoRequest.getDescription());
        try {
            todo = todoService.updateTodo(todo);
            return new ResponseEntity<>(todo, HttpStatus.OK);
        } catch (HttpClientErrorException ex) {
            return new ResponseEntity<>(ex.getStatusCode());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTodo(@PathVariable("id") UUID id) {
        try {
            todoService.deleteTodo(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (HttpClientErrorException ex) {
            return new ResponseEntity<>(ex.getStatusCode());
        }
    }
}