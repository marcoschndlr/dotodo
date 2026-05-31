package social.marco.dotodo.todo.repositories;

import java.util.List;
import java.util.UUID;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import social.marco.dotodo.jooq.tables.records.TodosRecord;
import social.marco.dotodo.todo.model.Todo;

import static social.marco.dotodo.jooq.tables.Todos.TODOS;

@Repository
public class TodoRepository {

    private final DSLContext dsl;

    public TodoRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<Todo> findAll() {
        return dsl.selectFrom(TODOS)
                .fetch(this::toTodo);
    }

    public Todo create(Todo todo) {
        return dsl.insertInto(TODOS)
                .set(TODOS.ID, todo.id())
                .set(TODOS.TITLE, todo.title())
                .set(TODOS.DESCRIPTION, todo.description())
                .set(TODOS.DUE_DATE, todo.dueDate())
                .set(TODOS.COMPLETED, todo.completed())
                .returning()
                .fetchOne(this::toTodo);
    }

    public boolean deleteById(UUID id) {
        return dsl.deleteFrom(TODOS)
                .where(TODOS.ID.eq(id))
                .execute() > 0;
    }

    public Todo updateCompletion(UUID id, boolean completed) {
        return dsl.update(TODOS)
                .set(TODOS.COMPLETED, completed)
                .where(TODOS.ID.eq(id))
                .returning()
                .fetchOne(this::toTodo);
    }

    private Todo toTodo(TodosRecord record) {
        return new Todo(
                record.getId(),
                record.getTitle(),
                record.getDescription(),
                record.getDueDate(),
                record.getCompleted()
        );
    }
}
