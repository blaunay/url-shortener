package com.notarius.urlshortener;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UrlShortenerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void givenUrlIsShortened_whenUrlIsUnshorten_thenOriginalUrlIsRetrieved() throws Exception {
        String urlToShorten = "https://www.google.ca/search?q=how+to+code";
        String payload = "{\"completeUrl\": \"" + urlToShorten + "\"}";
        String shortenedUrlId = "XmzpK8xdXb";

        mockMvc.perform(post("/url/shorten").contentType(MediaType.APPLICATION_JSON).content(payload)).andExpect(status().isOk()).andExpect(content().string("https://short.bl/" + shortenedUrlId));

        mockMvc.perform(get("/url/unshorten/" + shortenedUrlId).contentType(MediaType.APPLICATION_JSON).content(payload)).andExpect(status().isOk()).andExpect(content().string(urlToShorten));
    }
}
