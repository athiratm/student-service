package com.skiply.student_service.service;

import com.skiply.student_service.dto.StudentRequest;
import com.skiply.student_service.dto.StudentResponse;
import com.skiply.student_service.entity.Student;
import com.skiply.student_service.exception.ResourceNotFoundException;
import com.skiply.student_service.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public StudentResponse addStudent(StudentRequest request) {

        Student student = mapToEntity(request);
        Student saved = studentRepository.save(student);
        return mapToResponse(saved);
    }

    public StudentResponse getStudentById(Long studentId) {

        Student student = getStudent(studentId);
        return mapToResponse(student);
    }

    public StudentResponse updateStudent(Long studentId, StudentRequest request) {

        Student student = getStudent(studentId);

        student.setStudentName(request.studentName());
        student.setGrade(request.grade());
        student.setMobileNumber(request.mobileNumber());
        student.setSchoolName(request.schoolName());

        return mapToResponse((studentRepository.save(student)));
    }

    public void deleteStudent(Long studentId) {
        Student student = getStudent(studentId);
        studentRepository.delete(student);
    }

    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    private Student getStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
        return student;
    }

    private Student mapToEntity(StudentRequest request) {
        Student student = new Student();
        student.setStudentId(request.studentId());
        student.setStudentName(request.studentName());
        student.setGrade(request.grade());
        student.setMobileNumber(request.mobileNumber());
        student.setSchoolName(request.schoolName());
        return student;
    }

    private StudentResponse mapToResponse(Student student) {
        return new StudentResponse(
                student.getStudentId(),
                student.getStudentName(),
                student.getGrade(),
                student.getMobileNumber(),
                student.getSchoolName()
        );
    }

}
