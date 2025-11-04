package org.labcabrera.sample.archetype.interfaces.http;

import org.junit.jupiter.api.Test;
import org.labcabrera.sample.archetype.interfaces.http.CounterController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;

@WebMvcTest(CounterController.class)
class HelloWorldControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnHelloWorld() throws Exception {
        mockMvc.perform(get("/api/v1/counters"))
            .andExpect(status().isOk());
    }

    @Test
    void shouldIncrementCounter() throws Exception {
        mockMvc.perform(post("/api/v1/counters/reactive-test"))
            .andExpect(status().isOk())
            .andExpect(content().string("1"));

        mockMvc.perform(post("/api/v1/counters/reactive-test"))
            .andExpect(status().isOk())
            .andExpect(content().string("2"));
    }

    @Test
    void shouldHandleMultipleDifferentCounters() throws Exception {
        mockMvc.perform(post("/api/v1/counters/counter-a"))
            .andExpect(status().isOk())
            .andExpect(content().string("1"));

        mockMvc.perform(post("/api/v1/counters/counter-b"))
            .andExpect(status().isOk())
            .andExpect(content().string("1"));

        mockMvc.perform(post("/api/v1/counters/counter-a"))
            .andExpect(status().isOk())
            .andExpect(content().string("2"));
    }
}