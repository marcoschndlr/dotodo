package social.marco.dotodo.todo.services;

import java.util.List;
import org.springframework.stereotype.Service;
import social.marco.dotodo.todo.model.Todo;
import social.marco.dotodo.todo.repositories.TodoRepository;

@Service
public class TodoService {

    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public List<Todo> getAllTodos() {
        return repository.findAll();
    }
}
