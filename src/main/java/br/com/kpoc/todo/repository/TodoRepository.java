package br.com.kpoc.todo.repository;

import br.com.kpoc.todo.model.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TodoRepository extends CrudRepository<Todo, UUID> {
    Optional<Todo> findByTitle(String title);
}
