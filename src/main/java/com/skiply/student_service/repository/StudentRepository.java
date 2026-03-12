package com.skiply.student_service.repository;

import com.skiply.student_service.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<Student, Long> {

}
