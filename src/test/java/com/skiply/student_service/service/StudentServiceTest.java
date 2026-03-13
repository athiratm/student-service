package com.skiply.student_service.service;

import com.skiply.student_service.dto.StudentRequest;
import com.skiply.student_service.dto.StudentResponse;
import com.skiply.student_service.entity.Student;
import com.skiply.student_service.exception.ResourceNotFoundException;
import com.skiply.student_service.exception.StudentAlreadyExistsException;
import com.skiply.student_service.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private StudentRequest studentRequest;
    private Student student;

    @BeforeEach
    void setUp() {

        studentRequest = new StudentRequest(1L, "John Doe", "10", "1234567890", "Test School");
        student = new Student();
        student.setStudentId(1L);
        student.setStudentName("John Doe");
        student.setGrade("10");
        student.setMobileNumber("1234567890");
        student.setSchoolName("Test School");
    }

    @Test
    void addStudent_ShouldReturnStudentResponse() {

        when(studentRepository.existsById(1L)).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        StudentResponse response = studentService.addStudent(studentRequest);

        assertNotNull(response);
        assertEquals(student.getStudentId(), response.studentId());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void addStudent_ShouldThrowException_WhenStudentExists() {

        when(studentRepository.existsById(1L)).thenReturn(true);

        assertThrows(StudentAlreadyExistsException.class, () -> studentService.addStudent(studentRequest));
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void getStudentById_ShouldReturnStudentResponse() {

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        StudentResponse response = studentService.getStudentById(1L);

        assertNotNull(response);
        assertEquals(student.getStudentId(), response.studentId());
    }

    @Test
    void getStudentById_ShouldThrowException_WhenStudentNotFound() {

        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> studentService.getStudentById(1L));
    }

    @Test
    void updateStudent_ShouldReturnUpdatedStudentResponse() {

        when(studentRepository.existsById(1L)).thenReturn(true);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        StudentResponse response = studentService.updateStudent(1L, studentRequest);

        assertNotNull(response);
        assertEquals(student.getStudentId(), response.studentId());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void updateStudent_ShouldThrowException_WhenStudentNotFound() {

        when(studentRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> studentService.updateStudent(1L, studentRequest));
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void deleteStudent_ShouldDeleteStudent() {

        when(studentRepository.existsById(1L)).thenReturn(true);

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteStudent_ShouldThrowException_WhenStudentNotFound() {

        when(studentRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> studentService.deleteStudent(1L));
        verify(studentRepository, never()).deleteById(1L);
    }

    @Test
    void getAllStudents_ShouldReturnPageOfStudentResponse() {

        Page<Student> page = new PageImpl<>(Collections.singletonList(student));
        when(studentRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<StudentResponse> response = studentService.getAllStudents(0, 10);

        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
    }
}
