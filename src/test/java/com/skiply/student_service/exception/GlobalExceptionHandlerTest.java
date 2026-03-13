package com.skiply.student_service.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(new DummyStudentController())
                .setControllerAdvice(globalExceptionHandler)
                .build();
    }

    @Test
    void handleNotFound_ShouldReturnNotFound() throws Exception {

        mockMvc.perform(get("/not-found")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("Resource not found"));
    }

    @Test
    void handleStudentAlreadyExists_ShouldReturnConflict() throws Exception {

        mockMvc.perform(get("/student-already-exists")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(HttpStatus.CONFLICT.value()))
                .andExpect(jsonPath("$.message").value("Student already exists"));
    }

    @Test
    void handleGlobalException_ShouldReturnInternalServerError() throws Exception {

        mockMvc.perform(get("/internal-server-error")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"));
    }

    @RestController
    public static class DummyStudentController {
        @GetMapping("/not-found")
        public void throwResourceNotFoundException() {
            throw new ResourceNotFoundException("Resource not found");
        }

        @GetMapping("/student-already-exists")
        public void throwStudentAlreadyExistsException() {
            throw new StudentAlreadyExistsException("Student already exists");
        }

        @GetMapping("/internal-server-error")
        public void throwException() throws RuntimeException {
            throw new RuntimeException("An unexpected error occurred");
        }
    }
}
