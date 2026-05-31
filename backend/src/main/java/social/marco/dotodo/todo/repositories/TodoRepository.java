package social.marco.dotodo.todo.repositories;

import java.util.List;
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

    private Todo toTodo(TodosRecord record) {
        return new Todo(
                record.getId(),
                record.getTitle(),
                record.getDescription(),
                record.getDueDate()
        );
    }
}
