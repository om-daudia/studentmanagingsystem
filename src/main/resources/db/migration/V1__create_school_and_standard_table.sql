--school table
CREATE TABLE IF NOT EXISTS school(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    school_name VARCHAR(100) NOT NULL
);

--standard table
CREATE TABLE IF NOT EXISTS standard (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    standard INT NOT NULL,
    school_id INT,
    CONSTRAINT fk_school
        FOREIGN KEY (school_id)
        REFERENCES school(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);