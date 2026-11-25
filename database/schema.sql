-- ==========================================
-- LEVEL 1: STUDENT PERFORMANCE MANAGEMENT
-- PostgreSQL Database
-- ==========================================

-- 1. BẢNG STUDENT
CREATE TABLE IF NOT EXISTS students (
    student_id VARCHAR(6),
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,

    CONSTRAINT pk_student PRIMARY KEY (student_id),

    CONSTRAINT chk_student_id
        CHECK (student_id ~ '^SV[0-9]{4}$'),

    CONSTRAINT chk_first_name
        CHECK (LENGTH(TRIM(first_name)) > 0),

    CONSTRAINT chk_last_name
        CHECK (LENGTH(TRIM(last_name)) > 0)
);

-- 2. BẢNG SUBJECT
CREATE TABLE IF NOT EXISTS subjects (
    subject_code VARCHAR(20),
    subject_name VARCHAR(100) NOT NULL,

    CONSTRAINT pk_subject PRIMARY KEY (subject_code),

    CONSTRAINT uq_subject_name UNIQUE (subject_name),

    CONSTRAINT chk_subject_name
        CHECK (LENGTH(TRIM(subject_name)) > 0)
);

-- 3. BẢNG GRADE
CREATE TABLE IF NOT EXISTS grades (
    student_id VARCHAR(6) NOT NULL,
    subject_code VARCHAR(20) NOT NULL,
    score NUMERIC(4,2) NOT NULL,

    CONSTRAINT pk_grade
        PRIMARY KEY (student_id, subject_code),

    CONSTRAINT fk_grade_student
        FOREIGN KEY (student_id)
        REFERENCES students(student_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_grade_subject
        FOREIGN KEY (subject_code)
        REFERENCES subjects(subject_code)
        ON DELETE RESTRICT,

    CONSTRAINT chk_grade_score
        CHECK (score BETWEEN 0 AND 10)
);

-- 4. DỮ LIỆU MÔN HỌC
INSERT INTO subjects (subject_code, subject_name)
VALUES
    ('JAVA', 'Java Programming'),
    ('DATABASE', 'Database Systems'),
    ('DSA', 'Data Structures and Algorithms')
ON CONFLICT (subject_code) DO NOTHING;