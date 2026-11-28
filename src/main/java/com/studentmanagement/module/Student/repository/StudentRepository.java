
package com.studentmanagement.module.Student.repository;

import com.studentmanagement.module.Student.IStudentRepository;
import com.studentmanagement.module.Student.model.Student;
import com.studentmanagement.shared.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepository implements IStudentRepository {

    // ==================== SQL QUERIES ====================

    private static final String SAVE_SQL = """
            INSERT INTO students (student_id, first_name, last_name)
            VALUES (?, ?, ?)
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT student_id, first_name, last_name
            FROM students
            WHERE student_id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT student_id, first_name, last_name
            FROM students
            ORDER BY student_id
            """;

    private static final String FIND_BY_NAME_SQL = """
            SELECT student_id, first_name, last_name
            FROM students
            WHERE first_name ILIKE ?
              AND last_name ILIKE ?
            ORDER BY student_id
            """;

    private static final String UPDATE_SQL = """
            UPDATE students
            SET first_name = ?, last_name = ?
            WHERE student_id = ?
            """;

    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM students
            WHERE student_id = ?
            """;

    private static final String EXISTS_BY_ID_SQL = """
            SELECT EXISTS (
                SELECT 1
                FROM students
                WHERE student_id = ?
            )
            """;

    private static final String COUNT_SQL = """
            SELECT COUNT(*)
            FROM students
            """;

    // ==================== PRIVATE HELPERS ====================

    // Chuyển một dòng ResultSet thành đối tượng Student
    private Student mapRow(ResultSet rs) throws SQLException {
        return new Student(
                rs.getString("student_id"),
                rs.getString("first_name"),
                rs.getString("last_name")
        );
    }

    // Dùng chung cho INSERT, UPDATE và DELETE
    private boolean executeUpdate(
            String sql,
            String operation,
            String... parameters
    ) {
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            // Gán lần lượt các tham số cho PreparedStatement
            for (int i = 0; i < parameters.length; i++) {
                statement.setString(i + 1, parameters[i]);
            }

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Failed to " + operation + " student", e
            );
        }
    }

    // ==================== CRUD OPERATIONS ====================

    @Override
    public boolean save(Student student) {
        return executeUpdate(
                SAVE_SQL,
                "save",
                student.studentId(),
                student.firstName(),
                student.lastName()
        );
    }

    @Override
    public Optional<Student> findById(String id) {
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(FIND_BY_ID_SQL)
        ) {
            statement.setString(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Failed to find student by ID: " + id, e
            );
        }
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(FIND_ALL_SQL);
                ResultSet rs = statement.executeQuery()
        ) {
            while (rs.next()) {
                students.add(mapRow(rs));
            }

            return students;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Failed to retrieve students", e
            );
        }
    }

    @Override
    public List<Student> findByName(
            String lastName,
            String firstName
    ) {
        List<Student> students = new ArrayList<>();

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(FIND_BY_NAME_SQL)
        ) {
            // Tìm kiếm gần đúng, không phân biệt hoa thường
            statement.setString(1, "%" + firstName + "%");
            statement.setString(2, "%" + lastName + "%");

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    students.add(mapRow(rs));
                }
            }

            return students;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Failed to find students by name", e
            );
        }
    }

    @Override
    public boolean update(Student student) {
        return executeUpdate(
                UPDATE_SQL,
                "update",
                student.firstName(),
                student.lastName(),
                student.studentId()
        );
    }

    @Override
    public boolean deleteById(String id) {
        return executeUpdate(
                DELETE_BY_ID_SQL,
                "delete",
                id
        );
    }

    @Override
    public boolean existById(String id) {
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(EXISTS_BY_ID_SQL)
        ) {
            statement.setString(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }

            throw new IllegalStateException(
                    "EXISTS query returned no result"
            );

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Failed to check student existence: " + id, e
            );
        }
    }

    // Đếm tổng số sinh viên
    public long count() {
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(COUNT_SQL);
                ResultSet rs = statement.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getLong(1);
            }

            throw new IllegalStateException(
                    "COUNT query returned no result"
            );

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Failed to count students", e
            );
        }
    }
}