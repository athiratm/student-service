package com.skiply.student_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "students")
@Data
@AllArgsConstructor
public class Student {
    @Id
    private String studentId;
    private String studentName;
    private String grade;
    private String mobileNumber;
    private String schoolName;
}
