package com.skiply.student_service.controller;

import com.skiply.student_service.dto.StudentRequest;
import com.skiply.student_service.dto.StudentResponse;
import com.skiply.student_service.entity.Student;
import com.skiply.student_service.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping()
    @Operation(summary = "Add a new student", description = "Creates a student record in the student table")
    public ResponseEntity<StudentResponse> addStudent(@Valid @RequestBody StudentRequest request) {

        StudentResponse studentReponse = studentService.addStudent(request);
        return new ResponseEntity<>(studentReponse, HttpStatus.CREATED);
    }

    @GetMapping("/{studentId}")
    @Operation(summary = "Retrieve a student by Id", description = "Retrieves a student record from the student table by its Id")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable Long studentId) {

        return ResponseEntity.ok(studentService.getStudentById(studentId));

    }

    @PutMapping("/{studentId}")
    @Operation(summary = "Update an existing student", description = "Updates student details in the student table")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable Long studentId, @RequestBody StudentRequest request) {

        return ResponseEntity.ok(studentService.updateStudent(studentId, request));
    }


    @GetMapping
    @Operation(summary = "List all students", description = "Retrieves all student records in student table")
    public ResponseEntity<List<StudentResponse>> getAllStudents() {

        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @DeleteMapping("/{studentId}")
    @Operation(summary = "Delete a student", description = "Removes a student record from student table")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {

        studentService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();

    }
}
