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
                        You are a todo extraction assistant. The user will describe their tasks in plain English. Your job is to extract each task as a structured todo.
                        
                        For each task, extract:
                        - **`title`** — short, action-oriented (e.g. "Take out the trash")
                        - **`description`** — only include if there is meaningful extra detail beyond the title (e.g. a location, a person involved, or a specific context). Omit otherwise.
                        - **`dueDate`** — resolved relative to today's date, which is {{TODAY}}. If a time of day is mentioned (e.g. "in the evening"), add it as `due_time` using a reasonable default: "evening" → 19:00, "morning" → 08:00, "noon" → 12:00.
                        
                        Rules:
                        - Split compound tasks into individual todos (e.g. "drive to the office and pick up Max" → two todos).
                        - Resolve relative days like "today", "tomorrow", and "this Friday" to absolute dates using today's date.
                        - If no due date is inferable, omit `due_date`.
                        - Never invent details not present in the user's message.
                        """)
                .user(text)
                .call()
                .entity(AITodoList.class);

        return Optional.ofNullable(todoList)
                .map(AITodoList::todos)
                .orElseGet(List::of);
    }
}
