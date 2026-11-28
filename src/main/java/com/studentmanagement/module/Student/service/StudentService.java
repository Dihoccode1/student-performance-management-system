package com.studentmanagement.module.Student.service;

import com.studentmanagement.module.Student.IStudentRepository;
import com.studentmanagement.module.Student.model.Student;
import com.studentmanagement.module.Student.validation.StudentValidator;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class StudentService {
    private final IStudentRepository repository;


    public StudentService(IStudentRepository repository) {
        this.repository = repository;
    }

    public Student createStudent(Student student) {
        if (!StudentValidator.isValidStudent(student))
            throw new IllegalArgumentException("Thông tin sinh viên không hợp lệ");
        if (repository.existById(student.studentId())) throw new IllegalArgumentException("Trùng ID sinh viên đã có");
        if (!repository.save(student)) throw new IllegalStateException("Không thể tạo sinh viên");
        return student;
    }

    public Student updateStudent(Student student) {
        if (!StudentValidator.isValidStudent(student))
            throw new IllegalStateException("Thông tin sinh viên không hợp lệ");
        if (!repository.update(student)) throw new NoSuchElementException("không tim thấy sinh viên");
        return student;
    }

    public Student deleteStudent(Student student) {
        if (!StudentValidator.isValidStudent(student))
            throw new IllegalArgumentException("Thông tin sinh viên không hợp lệ");
        if (!repository.deleteById(student.studentId())) throw new NoSuchElementException("Không tìm thấy sinh viên");
        return student;
    }

    public Student getStudentById(Student student) {
        if (!StudentValidator.isValidStudent(student)) throw new IllegalArgumentException("không tìm thấy sinh viên");

        return repository.findById(student.studentId()).orElseThrow(() -> new NoSuchElementException("Không thấy sinh viên"));
    }

    public List<Student> searchStudentByName(String firstName, String lastName) {
        if (!StudentValidator.isValidName(firstName) && !StudentValidator.isValidName(lastName))
            throw new IllegalArgumentException("Thông tin không hợp lệ");
        return repository.findByName(firstName, lastName);
    }

    public List<Student> getAllStudent() {
        return repository.findAll();
    }

    public List<Student> getStudentSortByName() {
        return repository.findAll().stream().sorted(Comparator.comparing(Student::firstName, String.CASE_INSENSITIVE_ORDER).thenComparing(Student::lastName, String.CASE_INSENSITIVE_ORDER)).toList();
    }

    public long countStudents() {
        return repository.count();
    }
}
