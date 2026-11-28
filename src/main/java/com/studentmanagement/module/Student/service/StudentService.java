package com.studentmanagement.module.Student.service;

import com.studentmanagement.module.Student.IStudentRepository;
import com.studentmanagement.module.Student.model.Student;

public class StudentService {
    private final IStudentRepository repository;


    private StudentService(IStudentRepository repository) {
        this.repository = repository;
    }

    public Student createStudent() {
        return null;
    }
}
