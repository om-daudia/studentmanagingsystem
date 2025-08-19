CREATE TABLE IF NOT EXISTS top_student_standard(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    student_id INT,
    student_name VARCHAR(100),
    percentage FLOAT,
    academic_year INT
);