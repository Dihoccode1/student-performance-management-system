package com.studentmanagement.module.Grade.model;

import java.math.BigDecimal;

public record Grade(String studentId, String subjectCode, BigDecimal score) {
}
