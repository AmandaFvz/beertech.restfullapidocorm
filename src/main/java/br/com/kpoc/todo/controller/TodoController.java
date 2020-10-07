package br.com.kpoc.todo.controller;

import br.com.kpoc.todo.controller.dto.TodoDto;
import br.com.kpoc.todo.model.Todo;
import br.com.kpoc.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void createTodo(@RequestBody TodoDto todoRequest) {
        Todo todo = new Todo(todoRequest.getTitle(), todoRequest.getDescription());
        todoService.createTodo(todo);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void updateTodo(@PathVariable("id") UUID id, @RequestBody TodoDto todoRequest) {
        Todo todo = new Todo();
        todo.setId(id);
        todo.setTitle(todoRequest.getTitle());
        todo.setDescription(todoRequest.getDescription());
        todoService.updateTodo(todo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void deleteTodo(@PathVariable("id") UUID id) {
        todoService.deleteTodo(id);
    }
}