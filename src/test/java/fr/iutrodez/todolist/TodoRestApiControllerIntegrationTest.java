package fr.iutrodez.todolist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class TodoRestApiControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository todoRepository;

    @BeforeEach
    void setUp() {
        todoRepository.deleteAll();

        Todo todo = new Todo();
        todo.setTitle("Integration Test Todo");
        todo.setDescription("Integration Test Description");
        todo.setDone(false);
        todoRepository.save(todo);
    }

    @Test
    public void testReadAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/todos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Integration Test Todo"));
    }

    @Test
    public void testCreate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"New Todo\", \"description\":\"New Description\", \"done\":false}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("New Todo"));
    }

    @Test
    public void testDelete() throws Exception {
        Todo newTodo = new Todo();
        newTodo.setTitle("Delete Test Todo");
        newTodo.setDescription("Delete Test Description");
        newTodo.setDone(false);
        Todo savedTodo = todoRepository.save(newTodo);

        mockMvc.perform(MockMvcRequestBuilders.delete("/todos/" + savedTodo.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
