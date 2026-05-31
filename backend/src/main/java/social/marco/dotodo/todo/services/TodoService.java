package social.marco.dotodo.todo.services;

import org.springframework.ai.chat.client.AdvisorParams;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import social.marco.dotodo.todo.model.AITodoItem;
import social.marco.dotodo.todo.model.AITodoList;
import social.marco.dotodo.todo.model.CreateTodoRequest;
import social.marco.dotodo.todo.model.Todo;
import social.marco.dotodo.todo.repositories.TodoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TodoService {

    private final TodoRepository repository;
    private final ChatClient chatClient;

    public TodoService(TodoRepository repository, ChatClient.Builder chatClientBuilder) {
        this.repository = repository;
        this.chatClient = chatClientBuilder.build();
    }

    public List<Todo> getAllTodos() {
        return repository.findAll();
    }

    @Transactional
    public void createTodos(CreateTodoRequest request) {
        var todos = generateTodosFromText(request.text());
        todos.forEach(todo -> {
            if (StringUtils.hasText(todo.title())) {
                persistTodoItem(todo);
            }
        });
    }

    public boolean deleteTodo(UUID id) {
        return repository.deleteById(id);
    }

    public Optional<Todo> updateCompletion(UUID id, boolean completed) {
        return Optional.ofNullable(repository.updateCompletion(id, completed));
    }

    private void persistTodoItem(AITodoItem item) {
        var dto = new Todo(
                UUID.randomUUID(),
                item.title(),
                item.description(),
                item.dueDate(),
                false
        );
        repository.create(dto);
    }

    private List<AITodoItem> generateTodosFromText(String text) {
        var todoList = chatClient.prompt()
                .advisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT)
                .system("""
                        You convert free-text into structured todos.
                        Extract tasks, infer deadlines when possible.
                        Omit descriptions when the task is appropriately described using only the title.
                        """)
                .user(text)
                .call()
                .entity(AITodoList.class);

        return Optional.ofNullable(todoList)
                .map(AITodoList::todos)
                .orElseGet(List::of);
    }
}
