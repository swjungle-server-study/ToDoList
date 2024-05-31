package jungle.study.todo.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jungle.study.todo.application.service.ToDoCommandService;
import jungle.study.todo.domain.Category;
import jungle.study.todo.domain.ToDo;
import jungle.study.todo.domain.ToDoEssential;
import jungle.study.todo.domain.repository.ToDoRepository;
import jungle.study.todo.presentation.ToDoController;
import jungle.study.todo.presentation.dto.request.CreateToDoReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ToDoServiceTest {

    @Autowired
    ToDoRepository toDoRepository;

    @Test
    @DisplayName("투두 단건 생성 API DB 연결")
    public void createTodoConnectionOnRepository(){
        //given
        LocalDate now = LocalDate.now();
        ToDo toDo = new ToDo(UUID.randomUUID(),
                new ToDoEssential(
                        "테스트 제목",
                        "테스트 내용",
                        Category.DOING,
                        now,
                        now.getDayOfWeek())
        );
        //when
        ToDo createdToDo = toDoRepository.createToDo(toDo);

        // then
        assertNotNull(createdToDo);
        assertEquals(2, toDoRepository.todoSeq.get());
    }
}
