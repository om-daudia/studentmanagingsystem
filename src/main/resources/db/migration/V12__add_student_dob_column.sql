--ALTER TABLE student
--ADD COLUMN date_of_birth DATE;

DO $$
BEGIN
-- check student table exist
    IF NOT EXISTS (
        SELECT FROM information_schema.tables
        WHERE table_schema = 'public' AND table_name = 'student'
    ) THEN
        CREATE TABLE student(
            id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
            student_name VARCHAR(100) NOT NULL,
            obtain_marks FLOAT,
            percentage FLOAT,
            result VARCHAR(10),
            date_of_birth DATE,
            division_id INT,
            CONSTRAINT fk_division
                FOREIGN KEY (division_id)
                REFERENCES division(id)
                ON DELETE CASCADE
                ON UPDATE CASCADE
        );
    ELSE
--    check date_of_birth column exist
        IF NOT EXISTS (
            SELECT FROM information_schema.columns
            WHERE table_schema = 'public'
              AND table_name = 'student'
              AND column_name = 'date_of_birth'
        ) THEN
            -- Add the column
            ALTER TABLE student ADD COLUMN date_of_birth DATE;
        END IF;
    END IF;
END
$$;
