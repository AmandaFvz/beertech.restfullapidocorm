package br.com.kpoc.todo.controller;

import br.com.kpoc.todo.controller.dto.TodoDto;
import br.com.kpoc.todo.model.Todo;
import br.com.kpoc.todo.service.TodoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

@Controller
@RequestMapping("/todo")
@Api(value = "Set of endpoints for recording Todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @ApiOperation(value = "Creates Todo")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Todo created"),
            @ApiResponse(code = 409, message = "Same title already exists")
    })
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

    @ApiOperation(value = "Updates Todo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Todo updated"),
            @ApiResponse(code = 404, message = "Id not found")
    })
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

    @ApiOperation(value = "Delete Todo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Todo deleted"),
            @ApiResponse(code = 404, message = "Id not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deleteTodo(@PathVariable("id") UUID id) {
        try {
            todoService.deleteTodo(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (HttpClientErrorException ex) {
            return new ResponseEntity<>(ex.getStatusCode());
        }
    }

    @ApiOperation(value = "List Todos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Todo list")
    })
    @GetMapping
    public ResponseEntity<Iterable<Todo>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAll());
    }

    @ApiOperation(value = "Get Todo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Todo"),
            @ApiResponse(code = 404, message = "Not found"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(todoService.getById(id));
        } catch (HttpClientErrorException ex) {
            return new ResponseEntity<>(ex.getStatusCode());
        }

    }
}