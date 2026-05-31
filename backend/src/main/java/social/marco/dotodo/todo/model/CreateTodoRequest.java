package social.marco.dotodo.todo.model;

import java.time.OffsetDateTime;

public record CreateTodoRequest(String title, String description, OffsetDateTime dueDate) {
}
