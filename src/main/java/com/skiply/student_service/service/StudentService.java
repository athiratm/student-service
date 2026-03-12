package com.skiply.student_service.service;


import com.skiply.student_service.dto.StudentRequest;
import com.skiply.student_service.dto.StudentResponse;
import com.skiply.student_service.entity.Student;
import com.skiply.student_service.exception.ResourceNotFoundException;
import com.skiply.student_service.exception.StudentAlreadyExistsException;
import com.skiply.student_service.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);


    /**
     * To Add a new student to the repository.
     *
     * @param request The student request object containing the student details.
     * @return The student response object containing the saved student details.
     */
    public StudentResponse addStudent(StudentRequest request) {

        logger.info("Adding student: {}", request);
        if (studentRepository.existsById(request.studentId())) {
            logger.warn("Student ID {} already exists in the system", request.studentId());
            throw new StudentAlreadyExistsException("Student with ID " + request.studentId() + " is already registered.");
        }
        Student student = mapToEntity(request);
        Student savedStudent = studentRepository.save(student);
        logger.info("Student added successfully: {}", savedStudent);
        return mapToResponse(savedStudent);
    }

    /**
     * To retrieve a student by their ID.
     *
     * @param studentId The ID of the student to retrieve.
     * @return The student response object containing the student details.
     */
    public StudentResponse getStudentById(Long studentId) {

        logger.info("Getting student by ID: {}", studentId);
        Student student = getStudent(studentId);
        logger.info("Found student: {}", student);
        return mapToResponse(student);
    }

    /**
     * To update an existing student's details.
     *
     * @param studentId The ID of the student to update.
     * @param request   The student request object containing the updated details.
     * @return The student response object containing the updated student details.
     */
    public StudentResponse updateStudent(Long studentId, StudentRequest request) {

        logger.info("Updating student with ID {}: {}", studentId, request);
        Student student = getStudent(studentId);

        student.setStudentName(request.studentName());
        student.setGrade(request.grade());
        student.setMobileNumber(request.mobileNumber());
        student.setSchoolName(request.schoolName());
        Student updatedStudent = studentRepository.save(student);
        logger.info("Student updated successfully: {}", updatedStudent);
        return mapToResponse(updatedStudent);
    }

    /**
     * To delete a student by their ID.
     *
     * @param studentId The ID of the student to delete.
     */
    public void deleteStudent(Long studentId) {

        logger.info("Deleting student with ID: {}", studentId);
        Student student = getStudent(studentId);
        studentRepository.delete(student);
        logger.info("Student with ID {} deleted successfully", studentId);
    }

    /**
     * Retrieves all students from the repository.
     *
     * @return A list of student response objects containing the details of all students.
     */
    public List<StudentResponse> getAllStudents() {

        logger.info("Retrieving all students");
        List<Student> students = studentRepository.findAll();
        logger.info("Listed {} students", students.size());
        return students.stream().map(this::mapToResponse).toList();
    }

    /**
     * Helper method to get a student entity by ID, or throw an exception if not found.
     *
     * @param studentId The ID of the student to retrieve.
     * @return The student entity.
     * @throws ResourceNotFoundException if the student is not found.
     */
    private Student getStudent(Long studentId) {

        return studentRepository.findById(studentId).orElseThrow(() -> {
            logger.error("Student not found with id: {}", studentId);
            return new ResourceNotFoundException("Student not found with id: " + studentId);
        });
    }

    /**
     * Maps a StudentRequest object to a Student entity.
     *
     * @param request The student request object.
     * @return The mapped student entity.
     */
    private Student mapToEntity(StudentRequest request) {

        Student student = new Student();
        student.setStudentId(request.studentId());
        student.setStudentName(request.studentName());
        student.setGrade(request.grade());
        student.setMobileNumber(request.mobileNumber());
        student.setSchoolName(request.schoolName());
        return student;
    }

    /**
     * Maps a Student entity to a StudentResponse object.
     *
     * @param student The student entity.
     * @return The mapped student response object.
     */
    private StudentResponse mapToResponse(Student student) {

        return new StudentResponse(student.getStudentId(), student.getStudentName(), student.getGrade(), student.getMobileNumber(), student.getSchoolName());
    }

}
