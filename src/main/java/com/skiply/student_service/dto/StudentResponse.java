package com.skiply.student_service.dto;

public record StudentResponse(
        Long studentId,
        String studentName,
        String grade,
        String mobileNumber,
        String schoolName) {
}
