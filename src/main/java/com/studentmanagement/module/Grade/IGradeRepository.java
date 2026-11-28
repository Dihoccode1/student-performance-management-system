package com.studentmanagement.module.Grade;

import com.studentmanagement.module.Grade.model.Grade;

import java.util.List;
import java.util.Optional;

public interface IGradeRepository {
    boolean save();

    boolean update(Grade grade);

    Optional<Grade> findByStudentAndSubject(String studentId, String subjectCode);

    List<Grade> findByStudentId(String studentId);

    List<Grade> findBySubjectCode(String subjectCode);

    List<Grade> findAll();

    boolean delete(String studentId, String subjectCode);
}
