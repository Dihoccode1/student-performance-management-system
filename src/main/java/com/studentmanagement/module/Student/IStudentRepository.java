package com.studentmanagement.module.Student;

import com.studentmanagement.module.Student.model.Student;

import java.util.List;
import java.util.Optional;

public interface IStudentRepository {
    boolean save(Student student);

    Optional<Student> findById(String id);

    List<Student> findAll();

    List<Student> findByName(String lastName, String firstName);

    boolean update(Student student);

    boolean existById(String id);

    boolean deleteById(String id);

    long count();
}
