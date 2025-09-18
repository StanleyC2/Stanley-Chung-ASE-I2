package dev.coms4156.project.individualproject;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TestingWebApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void homeRouteTest() throws Exception {
        String welcomeMessage = "Welcome to the home page! In order to make an API call direct your browser or Postman to an endpoint.";
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(welcomeMessage)));
    }

    @Test
    void getBookTest() throws Exception{
        this.mockMvc.perform(get("/book/0"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Book not found")));

        this.mockMvc.perform(get("/book/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("title")));
    }

    @Test
    void addCopyTest() throws Exception{
        this.mockMvc.perform(patch("/book/0/add"))
                .andDo(print())
                .andExpect(status().isIAmATeapot())
                .andExpect(content().string(containsString("Book not found")));

        this.mockMvc.perform(patch("/book/1/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("title")));
    }

    @Test
    void getAvailableTest() throws Exception{
        this.mockMvc.perform(put("/books/available"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("copiesAvailable")));
    }

}