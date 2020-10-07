package br.com.kpoc.todo.service;

import br.com.kpoc.todo.model.Todo;
import br.com.kpoc.todo.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;
import java.util.UUID;

@Service
public class TodoService {
    private TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    public void createTodo(Todo todo){
        Optional<Todo> foundTodo = this.todoRepository.findByTitle(todo.getTitle());

        if(foundTodo.isPresent()){
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "Could not create Todo with the provided title");
        }

        this.todoRepository.save(todo);
    }

    public Todo updateTodo(Todo todo){
        Optional<Todo> foundTodo = this.todoRepository.findById(todo.getId());

        if(!foundTodo.isPresent()){
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Could not find the requested todo");
        }

        return this.todoRepository.save(todo);
    }

    public void deleteTodo(UUID id){
        Optional<Todo> foundTodo = this.todoRepository.findById(id);

        if(!foundTodo.isPresent()){
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Could not find the requested todo");
        }

        this.todoRepository.delete(foundTodo.get());
    }

}
