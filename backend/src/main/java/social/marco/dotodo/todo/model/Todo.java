package social.marco.dotodo.todo.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record Todo(UUID id, String title, String description, OffsetDateTime dueDate) {
}
