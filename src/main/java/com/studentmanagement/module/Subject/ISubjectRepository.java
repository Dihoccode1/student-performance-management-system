package com.studentmanagement.module.Subject;

import com.studentmanagement.module.Subject.model.Subject;

import java.util.List;
import java.util.Optional;

public interface ISubjectRepository {
    List<Subject> findAll();

    Optional<Subject> findByCode(String subjectCode);

    boolean exitsByCode(String subjectCode);
}
