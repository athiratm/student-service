package com.skiply.student_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skiply.student_service.dto.StudentRequest;
import com.skiply.student_service.dto.StudentResponse;
import com.skiply.student_service.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class StudentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void addStudent_ShouldReturnCreated() throws Exception {
        StudentRequest request = new StudentRequest(1L, "John Doe", "A", "1234567890", "School A");
        StudentResponse response = new StudentResponse(1L, "John Doe", "A", "1234567890", "School A");

        when(studentService.addStudent(any(StudentRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void getStudentById_ShouldReturnStudent() throws Exception {
        StudentResponse response = new StudentResponse(1L, "John Doe", "A", "1234567890", "School A");

        when(studentService.getStudentById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/students/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void updateStudent_ShouldReturnUpdatedStudent() throws Exception {
        StudentRequest request = new StudentRequest(1L, "John Doe", "B", "1234567890", "School A");
        StudentResponse response = new StudentResponse(1L, "John Doe", "B", "1234567890", "School A");

        when(studentService.updateStudent(eq(1L), any(StudentRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void shouldReturnPaginatedStudents11() throws Exception {

        StudentResponse student = new StudentResponse(111L, "John Doe", "Grade 10", "0501234567", "Skiply School");
        Page<StudentResponse> studentPage = new PageImpl<>(List.of(student), PageRequest.of(0, 10), 1);

        when(studentService.getAllStudents(0, 10)).thenReturn(studentPage);

        mockMvc.perform(get("/api/v1/students")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].studentId").value(111))
                .andExpect(jsonPath("$.content[0].studentName").value("John Doe"))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.size").value(10));
    }

    @Test
    void deleteStudent_ShouldReturnNoContent() throws Exception {

        doNothing().when(studentService).deleteStudent(1L);

        mockMvc.perform(delete("/api/v1/students/1"))
                .andExpect(status().isNoContent());
    }
}
