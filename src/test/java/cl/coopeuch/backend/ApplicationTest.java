package cl.coopeuch.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@SpringBootTest
class ApplicationTest {
    @Autowired
    MockMvc mock;

    @Test
    @Order(0)
    void getTasksList() throws Exception {
        mock.perform(get("/tasks/list")).andDo(print());
    }

    @Test
    @Order(1)
    void getTaskById() throws Exception {
        mock.perform(get("/tasks/detail/1")).andDo(print());
    }

    @Test
    @Order(2)
    void createTask() throws Exception {
        mock.perform(post("/tasks/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\n    \"description\": \"Task 15\",\n    \"enabled\": true\n}"))
                .andDo(print());
    }

    @Test
    @Order(3)
    void updateTask() throws Exception {
        mock.perform(put("/tasks/update")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\n    \"id\": 2,\n    \"description\": \"Task 15\",\n    \"enabled\": false\n}"))
                .andDo(print());
    }

    @Test
    @Order(4)
    void deleteTask() throws Exception {
        mock.perform(delete("/tasks/delete/1")).andDo(print());
    }
}