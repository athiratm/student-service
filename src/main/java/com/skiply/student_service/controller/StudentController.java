package com.skiply.student_service.controller;

import com.skiply.student_service.entity.Student;
import com.skiply.student_service.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    @Operation(summary = "Add a new student", description = "Creates a student record in the student table")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {

        Student studentAdded = studentService.addStudent(student);
        return new ResponseEntity<>(studentAdded, HttpStatus.CREATED);
    }

    @GetMapping("/{studentId}")
    @Operation(summary = "Retrieve a student by Id", description = "Retrieves a student record from the student table by its Id")
    public ResponseEntity<Student> getStudentById(@PathVariable String studentId) {

        return ResponseEntity.ok(studentService.getStudentById(studentId));

    }

    @PutMapping("/{studentId}")
    @Operation(summary = "Update an existing student", description = "Updates student details in the student table")
    public ResponseEntity<Student> updateStudent(@PathVariable String studentId, @RequestBody Student studentDetails) {

        return ResponseEntity.ok(studentService.updateStudent(studentId, studentDetails));
    }


    @GetMapping
    @Operation(summary = "List all students", description = "Retrieves all student records in student table")
    public ResponseEntity<List<Student>> getAllStudents() {

        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @DeleteMapping("/{studentId}")
    @Operation(summary = "Delete a student", description = "Removes a student record from student table")
    public ResponseEntity<Void> deleteStudent(@PathVariable String studentId) {

        studentService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();

    }
}
