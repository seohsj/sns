package com.hj.sns.photo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hj.sns.photo.model.Photo;
import com.hj.sns.photo.service.PhotoService;
import com.hj.sns.user.User;
import com.hj.sns.user.UserJpaRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Transactional
@SpringBootTest
class PhotoApiControllerTest {
    @Autowired
    private PhotoApiController photoApiController;
    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private PhotoService photoService;
    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach(){
        mockMvc= MockMvcBuilders.standaloneSetup(photoApiController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();

    }

    @Test
    @DisplayName("사진 저장")
    void uploadPhoto() throws Exception {

        User user = new User("userA", "password");
        User user2 = new User("userB", "password");
        userJpaRepository.save(user);
        userJpaRepository.save(user2);

        PhotoApiController.PhotoCreateRequest pr= new PhotoApiController.PhotoCreateRequest(user.getId(),"imagePath","content #content #abc");

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/photos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pr))
        ).andExpect(status().isCreated());


        PhotoApiController.PhotoCreateRequest pr2= new PhotoApiController.PhotoCreateRequest(user2.getId(),"imagePath","content #content #abc");
        mockMvc.perform(post("/api/photos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pr2))
        ).andExpect(status().isCreated());

    }

    //comment 추가
    @Test
    @DisplayName("user가 올린 사진 조회")
    void findPhotos() throws Exception {
        User user = new User("userA", "password");
        User user2 = new User("userB", "password");
        userJpaRepository.save(user);
        userJpaRepository.save(user2);
        photoService.save(user.getId(), "imagePath", "content#content");
        photoService.save(user.getId(), "imagePath", "#abc#cde");
        photoService.save(user.getId(), "imagePath", "content#content");

        mockMvc.perform(get("/api/photos/userA?page=0&size=3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].imagePath").value("imagePath"))
                .andExpect(jsonPath("$.content[0].content").value("content#content"))
                .andExpect(jsonPath("$.content[0].tags[0].tagName").value("content"))
                .andExpect(jsonPath("$.content[1].imagePath").value("imagePath"))
                .andExpect(jsonPath("$.content[1].content").value("#abc#cde"))
                .andExpect(jsonPath("$.content[1].tags[0].tagName").value("abc"))
                .andExpect(jsonPath("$.content[1].tags[1].tagName").value("cde"))
                .andExpect(jsonPath("$.content[2].imagePath").value("imagePath"))
                .andExpect(jsonPath("$.content[2].content").value("content#content"))
                .andExpect(jsonPath("$.content[2].tags[0].tagName").value("content"));
    }


}