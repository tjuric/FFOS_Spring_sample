package com.infobip.sample.ffosspring_sample;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This is a sample of a unit test
 */
@SpringBootTest
@AutoConfigureMockMvc
class FfosSpringSampleApplicationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getSampleText() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Pozdrav FFOS-u iz Spring Boot aplikacije! <br/>" +
                        "Brojevi na koje će se slati SMS: +385997778888,+385951112222")));
    }

}
