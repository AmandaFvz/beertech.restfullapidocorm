package br.com.kpoc.todo.controller;

import br.com.kpoc.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    @ResponseBody
//    public void createTodo(@RequestBody TodoDto){
//
//    }
}