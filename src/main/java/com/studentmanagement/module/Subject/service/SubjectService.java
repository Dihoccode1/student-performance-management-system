package com.studentmanagement.module.Subject.service;

import com.studentmanagement.module.Subject.ISubjectRepository;
import com.studentmanagement.module.Subject.model.Subject;

import java.util.List;
import java.util.NoSuchElementException;

public class SubjectService {
    private final ISubjectRepository repository;

    public SubjectService(ISubjectRepository repository) {
        this.repository = repository;
    }

    private void validateCode(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException(
                    "Mã môn học không được để trống"
            );
        }
    }

    public List<Subject> getAllSubjects() {
        return repository.findAll();
    }

    public Subject findByCode(String code) {
        return repository.findByCode(code).orElseThrow(() -> new NoSuchElementException("Không thấy lớp học"));
    }

    public boolean checkExistence(String code) {
        return repository.exitsByCode(code);
    }
}
