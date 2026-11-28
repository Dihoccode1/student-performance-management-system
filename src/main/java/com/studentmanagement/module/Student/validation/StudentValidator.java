package com.studentmanagement.module.Student.validation;

import com.studentmanagement.module.Student.model.Student;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public final class StudentValidator {
    private static final String REGEX_STUDENT_ID = "^SV[0-9]{4}";
    private static final Pattern PATTERN = Pattern.compile(REGEX_STUDENT_ID);

    private StudentValidator() {
    }

    public static boolean isValidStudent(Student student) {
        if (student == null) return false;
        return isValidName(student.firstName()) && isValidName(student.lastName()) && isValidStudentId(student.studentId());
    }

    public static boolean isValidStudentId(String studentId) {
        if (studentId == null) return false;
        try {
            Matcher matcher = PATTERN.matcher(studentId.strip());
            return matcher.matches();
        } catch (PatternSyntaxException e) {
            // Bắt lỗi nếu cú pháp Regex bị sai (phòng hờ khi sửa chuỗi REGEX sau này)
            throw new IllegalStateException("Cú pháp Regex kiểm tra mã sinh viên không hợp lệ", e);
        } catch (RuntimeException e) {
            // Bắt các lỗi runtime không mong muốn khác và bọc lại kèm nguyên nhân gốc (e)
            throw new IllegalStateException("Lỗi hệ thống khi kiểm tra mã sinh viên: " + studentId, e);
        }

    }

    public static boolean isValidName(String name) {
        return name != null
                && !name.isBlank()
                && name.strip().length() <= 50;
    }
}