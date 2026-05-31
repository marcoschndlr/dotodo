package social.marco.dotodo.todo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import social.marco.dotodo.todo.model.CreateTodoRequest;
import social.marco.dotodo.todo.model.Todo;
import social.marco.dotodo.todo.model.UpdateTodoCompletionRequest;
import social.marco.dotodo.todo.services.TodoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Todo> getTodos() {
        return service.getAllTodos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTodos(@RequestBody CreateTodoRequest request) {
        if (request == null || request.text() == null || request.text().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "text is required");
        }
        service.createTodos(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodo(@PathVariable UUID id) {
        if (!service.deleteTodo(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "todo not found");
        }
    }

    @PatchMapping("/{id}")
    public Todo updateCompletion(
            @PathVariable UUID id,
            @RequestBody UpdateTodoCompletionRequest request
    ) {
        if (request == null || request.completed() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "completed is required");
        }
        return service.updateCompletion(id, request.completed())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "todo not found"));
    }
}
