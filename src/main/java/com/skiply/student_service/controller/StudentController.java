package com.skiply.student_service.controller;

import com.skiply.student_service.dto.StudentRequest;
import com.skiply.student_service.dto.StudentResponse;
import com.skiply.student_service.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * The StudentController class handles all incoming HTTP requests for the student service.
 */
@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    /**
     * Creates a new student record.
     *
     * @param request The request body containing the student's details.
     * @return A response entity with the created student's details.
     */
    @PostMapping()
    @Operation(summary = "Add a new student", description = "Creates a student record in the student table")
    public ResponseEntity<StudentResponse> addStudent(@Valid @RequestBody StudentRequest request) {

        logger.info("Request to add a student with ID: {}", request.studentId());
        StudentResponse studentReponse = studentService.addStudent(request);
        return new ResponseEntity<>(studentReponse, HttpStatus.CREATED);
    }

    /**
     * Retrieves a student by their ID.
     *
     * @param studentId The ID of the student to retrieve.
     * @return A response entity with the student's details.
     */
    @GetMapping("/{studentId}")
    @Operation(summary = "Retrieve a student by Id", description = "Retrieves a student record from the student table by its Id")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable Long studentId) {

        logger.info("Request to get Student : {}", studentId);
        return ResponseEntity.ok(studentService.getStudentById(studentId));

    }

    /**
     * Updates an existing student's details.
     *
     * @param studentId The ID of the student to update.
     * @param request   The request body with the updated student details.
     * @return A response entity with the updated student's details.
     */
    @PutMapping("/{studentId}")
    @Operation(summary = "Update an existing student", description = "Updates student details in the student table")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable Long studentId, @Valid @RequestBody StudentRequest request) {

        logger.info("Request to update student with ID: {}", studentId);
        return ResponseEntity.ok(studentService.updateStudent(studentId, request));
    }


    /**
     * Retrieves a list of all students.
     *
     * @return A response entity with a list of all students.
     */
    @GetMapping
    @Operation(summary = "List all students", description = "Retrieves all student records in student table")
    public ResponseEntity<Page<StudentResponse>> getAllStudents(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        logger.info("Request to get all students");
        Page<StudentResponse> response = studentService.getAllStudents(page, size);
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a student by their ID.
     *
     * @param studentId The ID of the student to delete.
     * @return A response entity with no content.
     */
    @DeleteMapping("/{studentId}")
    @Operation(summary = "Delete a student", description = "Removes a student record from student table")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {

        logger.info("Request to delete student with ID: {}", studentId);
        studentService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();

    }
}
