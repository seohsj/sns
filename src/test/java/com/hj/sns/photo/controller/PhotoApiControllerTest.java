package com.hj.sns.photo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
class PhotoApiControllerTest {

    @Autowired
    private PhotoApiController photoApiController;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(photoApiController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();

    }

    @Test
    @DisplayName("사진 저장")
    void uploadPhoto() throws Exception {


        PhotoApiController.PhotoCreateRequest pr = new PhotoApiController.PhotoCreateRequest(1L, "imagePath", "content #content #abc");

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/photos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pr))
        ).andExpect(status().isCreated());


        PhotoApiController.PhotoCreateRequest pr2 = new PhotoApiController.PhotoCreateRequest(2L, "imagePath", "content #content #abc");
        mockMvc.perform(post("/api/photos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pr2))
        ).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("사진 업데이트")
    void updatePhoto() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        PhotoApiController.PhotoUpdateRequest pr = new PhotoApiController.PhotoUpdateRequest("newImagePath", "#new");
        mockMvc.perform(patch("/api/photos/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pr)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.photoId").value(1));
    }

    @Test
    @DisplayName("사진 삭제")
    void deletePhoto() throws Exception{
        mockMvc.perform(delete("/api/photos/" + 1))
                .andExpect(jsonPath("$.deleted").value("true"));
    }

    //comment 추가
    @Test
    @DisplayName("user가 올린 사진 조회")
    void findPhotos() throws Exception {


        mockMvc.perform(get("/api/photos/userA?page=0&size=3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].content").value("#tagA#tagB#tagC"))
                .andExpect(jsonPath("$.content[0].tags[0].tagName").value("tagA"))
                .andExpect(jsonPath("$.content[0].tags[1].tagName").value("tagB"))
                .andExpect(jsonPath("$.content[0].tags[2].tagName").value("tagC"))
                .andExpect(jsonPath("$.content[0].comments[0].content").value("comment1"))
                .andExpect(jsonPath("$.content[1].content").value("#tagA#tagB"))
                .andExpect(jsonPath("$.content[1].tags[0].tagName").value("tagA"))
                .andExpect(jsonPath("$.content[1].tags[1].tagName").value("tagB"))
                .andExpect(jsonPath("$.content[2].content").value("#tagC#tagD @userC"))
                .andExpect(jsonPath("$.content[2].tags[0].tagName").value("tagC"))
                .andExpect(jsonPath("$.content[2].tags[1].tagName").value("tagD"))
                .andExpect(jsonPath("$.content[2].comments[0].content").value("comment7"))
                .andExpect(jsonPath("$.content[2].mentionedUsers[0].username").value("userC"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("user의 피드 조회")
    void getFeed() throws Exception {

        mockMvc.perform(get("/api/feeds/userA?page=0&size=5&sort=id"))
                .andExpect(jsonPath("$.content[0].username").value("userA"))
                .andExpect(jsonPath("$.content[0].tags[0].tagName").value("tagA"))
                .andExpect(jsonPath("$.content[0].tags[1].tagName").value("tagB"))
                .andExpect(jsonPath("$.content[0].tags[2].tagName").value("tagC"))
                .andExpect(jsonPath("$.content[0].comments[0].content").value("comment1"))
                .andExpect(jsonPath("$.content[1].username").value("userA"))
                .andExpect(jsonPath("$.content[1].tags[0].tagName").value("tagA"))
                .andExpect(jsonPath("$.content[1].tags[1].tagName").value("tagB"))
                .andExpect(jsonPath("$.content[2].username").value("userA"))
                .andExpect(jsonPath("$.content[2].tags[0].tagName").value("tagC"))
                .andExpect(jsonPath("$.content[2].tags[1].tagName").value("tagD"))
                .andExpect(jsonPath("$.content[2].mentionedUsers[0].username").value("userC"))
                .andExpect(jsonPath("$.content[2].comments[0].content").value("comment7"))
                .andExpect(jsonPath("$.content[3].username").value("userB"))
                .andExpect(jsonPath("$.content[3].tags[0].tagName").value("tagA"))
                .andExpect(jsonPath("$.content[3].tags[1].tagName").value("tagE"))
                .andExpect(jsonPath("$.content[3].comments[0].content").value("comment2"))
                .andExpect(jsonPath("$.content[3].comments[1].content").value("comment6"))
                .andExpect(jsonPath("$.content[4].username").value("userB"))
                .andExpect(jsonPath("$.content[4].tags[0].tagName").value("tagE"))
                .andExpect(jsonPath("$.content[4].comments[0].content").value("comment3"))
                .andExpect(status().isOk())
                .andDo(print());

    }

}