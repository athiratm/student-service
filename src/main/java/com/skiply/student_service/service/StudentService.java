package com.skiply.student_service.service;

import com.skiply.student_service.entity.Student;
import com.skiply.student_service.exception.ResourceNotFoundException;
import com.skiply.student_service.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student addStudent(Student student) {

        return studentRepository.save(student);
    }

    public Student getStudentById(String studentId) {

        return studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
    }

    public Student updateStudent(String studentId, Student studentDetails) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));

        student.setStudentName(studentDetails.getStudentName());
        student.setGrade(studentDetails.getGrade());
        student.setMobileNumber(studentDetails.getMobileNumber());
        student.setSchoolName(studentDetails.getSchoolName());

        return studentRepository.save(student);
    }

    public void deleteStudent(String studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
        studentRepository.delete(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }



}
