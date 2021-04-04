package com.hj.sns.tag;

import com.hj.sns.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    private MockMvc mockMvc;

    @BeforeEach
    void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(tagApiController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();

    }

    @Test
    @DisplayName("사진을 태그로 조회한다.")
    void findPhotoByTag() throws Exception{
        mockMvc.perform(get("/api/tags/tagA/photos?page=0&size=3&sort=photo.id,desc"))
                .andDo(print())
                .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].photoId").value(7))
        .andExpect(jsonPath("$.content[1].photoId").value(4))
        .andExpect(jsonPath("$.content[2].photoId").value(2));
    }

    @Test
    @DisplayName("검색어를 포함하는 태그를 조회한다")
    void searchTag() throws Exception{
        mockMvc.perform(get("/api/tags/tag?page=0&size=3&sort=name,ASC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].tagId").value(1))
                .andExpect(jsonPath("$.content[1].tagId").value(2))
                .andExpect(jsonPath("$.content[2].tagId").value(3))
                .andExpect(jsonPath("$.content[0].tagName").value("tagA"))
                .andExpect(jsonPath("$.content[1].tagName").value("tagB"))
                .andExpect(jsonPath("$.content[2].tagName").value("tagC"))
                .andExpect(jsonPath("$.content[0].count").value(4))
                .andExpect(jsonPath("$.content[1].count").value(3))
                .andExpect(jsonPath("$.content[2].count").value(2));


    }
}