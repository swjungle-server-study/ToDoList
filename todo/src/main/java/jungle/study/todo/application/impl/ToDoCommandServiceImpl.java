package jungle.study.todo.application.impl;

import jungle.study.todo.application.service.ToDoCommandService;
import jungle.study.todo.domain.ToDo;
import jungle.study.todo.domain.ToDoEssential;
import jungle.study.todo.domain.exception.ToDoNotFoundException;
import jungle.study.todo.domain.repository.ToDoRepository;
import jungle.study.todo.presentation.dto.request.CreateToDoReq;
import jungle.study.todo.presentation.dto.request.ModifyToDoReq;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ToDoCommandServiceImpl implements ToDoCommandService {

    private final ToDoRepository toDoRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public UUID createToDo(CreateToDoReq createToDoReq) {
        LocalDate now = LocalDate.now();
        ToDo toDo = new ToDo(UUID.randomUUID(),
                new ToDoEssential(
                        createToDoReq.title(),
                        createToDoReq.contents(),
                        createToDoReq.category(),
                        now,
                        now.getDayOfWeek())
        );

        ToDo todo = toDoRepository.save(toDo);
        if (todo == null) {
            throw new ToDoNotFoundException();
        }

        return todo.getUuid();
    }

    @Override
    public ToDo modifyToDoEssential(ModifyToDoReq modifyToDoReq) {
        ToDo toDo = toDoRepository.findByUuid(modifyToDoReq.uuid()).orElseThrow(ToDoNotFoundException::new);
        toDo.modifyToDoEssential(modifyToDoReq.title(), modifyToDoReq.contents(), modifyToDoReq.category());
        return toDo;
    }

    @Override
    public void deleteToDo(UUID uuid) {
        ToDo toDo = toDoRepository.findByUuid(uuid).orElseThrow(ToDoNotFoundException::new);
        toDoRepository.delete(toDo);
    }

    @Override
    public boolean bulkInsertToDo(List<CreateToDoReq> createToDoReqs) {
        String todoBulkSql = "INSERT INTO todos (title,contents,category,postDate,dayOfWeek) VALUES(?,?,?,?,?)";
        int[] batchResult = jdbcTemplate.batchUpdate(todoBulkSql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                CreateToDoReq createToDoReq = createToDoReqs.get(i);
                ps.setString(1, createToDoReq.title());
                ps.setString(2, createToDoReq.contents());
                ps.setString(3, createToDoReq.category().toString());
                LocalDate now = LocalDate.now();
                ps.setDate(4, Date.valueOf(now));
                ps.setString(5, now.getDayOfWeek().toString());
            }

            @Override
            public int getBatchSize() {
                return createToDoReqs.size();
            }
        });
        for (int batch : batchResult) {
            if (batch <= 0) {
                return false;
            }
        }
        return true;
    }
}
