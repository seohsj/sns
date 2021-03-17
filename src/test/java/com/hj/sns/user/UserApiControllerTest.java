package com.hj.sns.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hj.sns.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class UserApiControllerTest {
    @Autowired
    private UserApiController userApiController;
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;
    @Autowired
    private UserService userService;
    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(userApiController)
                .setControllerAdvice(globalExceptionHandler)
                .build();
    }

    @DisplayName("회원가입 성공")
    @Test
    void join() throws Exception {
        UserApiController.UserJoinRequest request = new UserApiController.UserJoinRequest("userA", "password");
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @DisplayName("중복된 아이디(UserAlreadyExistException)로 회원가입 실패")
    @Test
    void joinFail() throws Exception {
        userService.save(new User("userA","password"));
        UserApiController.UserJoinRequest request = new UserApiController.UserJoinRequest("userA", "password");
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("이미 존재하는 회원입니다."));

    }


}