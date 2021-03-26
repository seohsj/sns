package com.hj.sns.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hj.sns.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
public class CommentApiControllerTest {
    @Autowired
    private CommentApiController commentApiController;
    private MockMvc mockMvc;
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentApiController)
                .setControllerAdvice(globalExceptionHandler)
                .build();
    }

    @Test
    @DisplayName("댓글 작성")
    void save() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CommentApiController.CommentCreateRequest pr = new CommentApiController.CommentCreateRequest(7L, "new Comment");
        mockMvc.perform(post("/api/photos/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pr)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteComment() throws Exception{
        mockMvc.perform(delete("/api/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deleted").value(true))
                .andDo(print());
    }
    @Test
    @DisplayName("댓글 삭제 실패(CommentNotFoundException)")
    void deleteCommentFail() throws Exception{
        mockMvc.perform(delete("/api/comments/21"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("등록되지 않은 comment_id 입니다."))
                .andDo(print());
    }
}
