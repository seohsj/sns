package com.hj.sns.follow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hj.sns.exception.GlobalExceptionHandler;
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

    @Autowired
    private FollowService followService;

    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private FollowJpaRepository followJpaRepository;

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
        for (char i = 'A'; i <= 'D'; i++) {
            String name = "user" + i;
            userJpaRepository.save(new User(name, "password"));
        }
        followService.follow("userA", "userB");
        followService.follow("userA", "userC");
        followService.follow("userA", "userD");

        mockMvc.perform(get("/api/userA/followings"))
                .andDo(print())
                .andExpect(jsonPath("$.content[0].username").value("userB"))
                .andExpect(jsonPath("$.content[1].username").value("userC"))
                .andExpect(jsonPath("$.content[2].username").value("userD"))
                .andExpect(jsonPath("$.pageable.offset").value(0))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/userA/followings?page=1&size=2"))
                .andDo(print())
                .andExpect(jsonPath("$.content[0].username").value("userD"))
                .andExpect(jsonPath("$.pageable.pageNumber").value(1))
                .andExpect(jsonPath("$.numberOfElements").value(1))
                .andExpect(status().isOk());

    }

    @Test
    void followerList() throws Exception {
        for (char i = 'A'; i <= 'D'; i++) {
            String name = "user" + i;
            userJpaRepository.save(new User(name, "password"));
        }

        followService.follow("userB", "userA");
        followService.follow("userC", "userA");
        followService.follow("userD", "userA");
        followService.follow("userA", "userD");

        mockMvc.perform(get("/api/userA/followers"))
                .andDo(print())
                .andExpect(jsonPath("$.content[0].username").value("userB"))
                .andExpect(jsonPath("$.content[1].username").value("userC"))
                .andExpect(jsonPath("$.content[2].username").value("userD"))
                .andExpect(jsonPath("$.pageable.offset").value(0))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/userA/followers?page=1&size=2"))
                .andDo(print())
                .andExpect(jsonPath("$.content[0].username").value("userD"))
                .andExpect(jsonPath("$.pageable.pageNumber").value(1))
                .andExpect(jsonPath("$.numberOfElements").value(1))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("follow요청 테스트")
    void follow() throws Exception {
        for (char i = 'A'; i <= 'D'; i++) {
            String name = "user" + i;
            userJpaRepository.save(new User(name, "password"));
        }

        FollowApiController.FollowRequest request = new FollowApiController.FollowRequest("userA", "userB");
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
        for (char i = 'A'; i <= 'C'; i++) {
            String name = "user" + i;
            userJpaRepository.save(new User(name, "password"));
        }
        followService.follow("userA", "userC");
        followService.follow("userA", "userB");


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