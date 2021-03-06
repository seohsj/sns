package com.hj.sns.follow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hj.sns.exception.GlobalExceptionHandler;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class FollowApiControllerTest {
    @Autowired
    private FollowApiController followApiController;
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(followApiController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(globalExceptionHandler)
                .build();
    }

    @Test
    void followingList() throws Exception {

        mockMvc.perform(get("/api/userA/followings"))
                .andDo(print())
                .andExpect(jsonPath("$.content[0].username").value("userB"))
                .andExpect(jsonPath("$.content[1].username").value("userC"))
                .andExpect(jsonPath("$.content[2].username").value("userD"))
                .andExpect(jsonPath("$.content[3].username").value("userE"))
                .andExpect(jsonPath("$.pageable.offset").value(0))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/userA/followings?page=1&size=2"))
                .andDo(print())
                .andExpect(jsonPath("$.content[0].username").value("userD"))
                .andExpect(jsonPath("$.content[1].username").value("userE"))
                .andExpect(jsonPath("$.pageable.pageNumber").value(1))
                .andExpect(jsonPath("$.numberOfElements").value(2))
                .andExpect(status().isOk());

    }

    @Test
    void followerList() throws Exception {


        mockMvc.perform(get("/api/userA/followers"))
                .andDo(print())
                .andExpect(jsonPath("$.content[0].username").value("userB"))
                .andExpect(jsonPath("$.pageable.offset").value(0))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/userE/followers?page=1&size=1"))
                .andDo(print())
                .andExpect(jsonPath("$.content[0].username").value("userF"))
                .andExpect(jsonPath("$.pageable.pageNumber").value(1))
                .andExpect(jsonPath("$.numberOfElements").value(1))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("follow요청 테스트")
    void follow() throws Exception {

        FollowApiController.FollowRequest request = new FollowApiController.FollowRequest("userA", "userG");
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/follows")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());

        FollowApiController.FollowRequest request2 = new FollowApiController.FollowRequest("userC", "userB");

        mockMvc.perform(post("/api/follows")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request2)))
                .andDo(print())
                .andExpect(status().isCreated());


        mockMvc.perform(get("/api/userB/followers"))
                .andDo(print())
                .andExpect(jsonPath("$.content[0].username").value("userA"))
                .andExpect(jsonPath("$.content[1].username").value("userC"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/userC/followings"))
                .andDo(print())
                .andExpect(jsonPath("$.content[0].username").value("userB"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("unfollow 요청 테스트")
    void unfollow() throws Exception {


        FollowApiController.UnfollowRequest request = new FollowApiController.UnfollowRequest("userA", "userB");
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(delete("/api/follows")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());

        FollowApiController.FollowRequest request2 = new FollowApiController.FollowRequest("userA", "userC");

        mockMvc.perform(delete("/api/follows")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request2)))
                .andDo(print())
                .andExpect(status().isOk());

        FollowApiController.FollowRequest request3 = new FollowApiController.FollowRequest("userC", "userA");

        mockMvc.perform(delete("/api/follows")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request3)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Follow관계가 아닙니다."));

    }
}