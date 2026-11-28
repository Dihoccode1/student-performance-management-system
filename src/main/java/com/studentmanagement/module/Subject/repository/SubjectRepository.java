
package com.studentmanagement.module.Subject.repository;

import com.studentmanagement.module.Subject.ISubjectRepository;
import com.studentmanagement.module.Subject.model.Subject;
import com.studentmanagement.shared.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubjectRepository implements ISubjectRepository {

    private static final String FIND_ALL_SQL = """
            SELECT subject_code, subject_name
            FROM subjects
            """;

    private static final String FIND_BY_CODE_SQL = """
            SELECT subject_code, subject_name
            FROM subjects
            WHERE subject_code = ?
            """;

    private static final String EXISTS_BY_CODE_SQL = """
            SELECT EXISTS (
                SELECT 1
                FROM subjects
                WHERE subject_code = ?
            )
            """;

    // Chuyển ResultSet thành Subject
    private Subject mapRow(ResultSet rs) throws SQLException {
        return new Subject(
                rs.getString("subject_code"),
                rs.getString("subject_name")
        );
    }

    @Override
    public List<Subject> findAll() {
        List<Subject> subjects = new ArrayList<>();

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(FIND_ALL_SQL);
                ResultSet rs = statement.executeQuery()
        ) {
            while (rs.next()) {
                subjects.add(mapRow(rs));
            }

            return subjects;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Failed to find all subjects", e
            );
        }
    }

    @Override
    public Optional<Subject> findByCode(String subjectCode) {
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(FIND_BY_CODE_SQL)
        ) {
            statement.setString(1, subjectCode);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Failed to find subject by code", e
            );
        }
    }

    @Override
    public boolean exitsByCode(String subjectCode) {
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(EXISTS_BY_CODE_SQL)
        ) {
            statement.setString(1, subjectCode);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }

            throw new IllegalStateException(
                    "Existence query returned no result"
            );

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Failed to check subject existence", e
            );
        }
    }
}
