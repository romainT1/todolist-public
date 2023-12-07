package fr.iutrodez.todolist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TodoRestApiController.class)
public class TodoRestApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoRepository todoRepository;

    private Todo todo;

    @BeforeEach
    void setUp() {
        todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Test Todo");
        todo.setDescription("Test Description");
        todo.setDone(false);
    }

    @Test
    public void testReadAll() throws Exception {
        Mockito.when(todoRepository.findAll()).thenReturn(Arrays.asList(todo));

        mockMvc.perform(MockMvcRequestBuilders.get("/todos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Test Todo"));
    }

    @Test
    public void testCreate() throws Exception {
        Mockito.when(todoRepository.save(Mockito.any(Todo.class))).thenReturn(todo);

        mockMvc.perform(MockMvcRequestBuilders.post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"New Todo\", \"description\":\"New Description\", \"done\":false}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Todo"));
    }

    @Test
    public void testDelete() throws Exception {
        Mockito.when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        Mockito.doNothing().when(todoRepository).deleteById(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/todos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
