package social.marco.dotodo.todo.model;

import java.time.OffsetDateTime;

public record AITodoItem(String title, String description, OffsetDateTime dueDate) {
}
