package com.hj.sns.tag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class TagApiControllerTest {
    @Autowired
    private TagApiController tagApiController;
    private MockMvc mockMvc;

    @BeforeEach
    void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(tagApiController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();

    }

    @Test
    void searchTag() throws Exception{
        mockMvc.perform(get("/api/tags/tagA?page=0&size=3&sort=photo.id,desc"))
                .andDo(print())
                .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].photoId").value(4))
        .andExpect(jsonPath("$.content[1].photoId").value(2))
        .andExpect(jsonPath("$.content[2].photoId").value(1));
    }
}