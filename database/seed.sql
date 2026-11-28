-- ==========================================
-- SEED DATA: STUDENT PERFORMANCE MANAGEMENT
-- ==========================================

-- 1. BẢNG SUBJECTS (Bổ sung thêm một số môn học để dữ liệu đa dạng)
INSERT INTO subjects (subject_code, subject_name)
VALUES
    ('JAVA', 'Java Programming'),
    ('DATABASE', 'Database Systems'),
    ('DSA', 'Data Structures and Algorithms'),
    ('WEB', 'Web Development'),
    ('OS', 'Operating Systems'),
    ('NETWORK', 'Computer Networks')
ON CONFLICT (subject_code) DO NOTHING;

-- 2. BẢNG STUDENTS (20 sinh viên hợp lệ theo định dạng ^SV[0-9]{4}$)
INSERT INTO students (student_id, first_name, last_name)
VALUES
    ('SV0001', 'An', 'Nguyễn Văn'),
    ('SV0002', 'Bình', 'Trần Thị'),
    ('SV0003', 'Cường', 'Lê Hoàng'),
    ('SV0004', 'Dung', 'Phạm Thùy'),
    ('SV0005', 'Em', 'Hoàng Minh'),
    ('SV0006', 'Giang', 'Vũ Hương'),
    ('SV0007', 'Hải', 'Đặng Thanh'),
    ('SV0008', 'Hương', 'Bùi Lan'),
    ('SV0009', 'Khang', 'Đỗ Gia'),
    ('SV0010', 'Linh', 'Hồ Khánh'),
    ('SV0011', 'Minh', 'Ngô Quang'),
    ('SV0012', 'Nga', 'Dương Quỳnh'),
    ('SV0013', 'Oanh', 'Lý Kim'),
    ('SV0014', 'Phúc', 'Trương Tấn'),
    ('SV0015', 'Quân', 'Võ Anh'),
    ('SV0016', 'Sơn', 'Phan Thái'),
    ('SV0017', 'Thảo', 'Trịnh Phương'),
    ('SV0018', 'Uyên', 'Đinh Nhã'),
    ('SV0019', 'Vinh', 'Lâm Thành'),
    ('SV0020', 'Yến', 'Mai Hải')
ON CONFLICT (student_id) DO NOTHING;

-- 3. BẢNG GRADES (Dữ liệu điểm từ 0.00 đến 10.00 cho 20 sinh viên)
INSERT INTO grades (student_id, subject_code, score)
VALUES
    ('SV0001', 'JAVA', 8.50),
    ('SV0001', 'DATABASE', 9.00),
    ('SV0001', 'DSA', 7.75),
    ('SV0002', 'JAVA', 6.50),
    ('SV0002', 'WEB', 8.00),
    ('SV0003', 'DATABASE', 5.25),
    ('SV0003', 'DSA', 4.50),
    ('SV0004', 'JAVA', 9.50),
    ('SV0004', 'OS', 8.75),
    ('SV0005', 'NETWORK', 7.00),
    ('SV0006', 'WEB', 8.25),
    ('SV0007', 'DSA', 6.00),
    ('SV0008', 'DATABASE', 9.25),
    ('SV0009', 'JAVA', 7.50),
    ('SV0010', 'OS', 8.00),
    ('SV0011', 'NETWORK', 6.75),
    ('SV0012', 'WEB', 9.00),
    ('SV0013', 'JAVA', 5.50),
    ('SV0014', 'DATABASE', 8.50),
    ('SV0015', 'DSA', 7.25),
    ('SV0016', 'OS', 6.25),
    ('SV0017', 'NETWORK', 8.75),
    ('SV0018', 'WEB', 9.50),
    ('SV0019', 'JAVA', 4.00),
    ('SV0020', 'DATABASE', 8.00)
ON CONFLICT (student_id, subject_code) DO NOTHING;