package social.marco.dotodo.todo.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import social.marco.dotodo.todo.model.CreateTodoRequest;
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

    public Todo createTodo(CreateTodoRequest request) {
        Todo todo = new Todo(
                UUID.randomUUID(),
                request.title(),
                request.description(),
                request.dueDate(),
                false
        );
        return repository.create(todo);
    }

    public boolean deleteTodo(UUID id) {
        return repository.deleteById(id);
    }

    public Optional<Todo> updateCompletion(UUID id, boolean completed) {
        return Optional.ofNullable(repository.updateCompletion(id, completed));
    }
}
