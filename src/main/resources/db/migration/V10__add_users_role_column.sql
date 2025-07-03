--add role column to users table
ALTER TABLE users
ADD COLUMN role VARCHAR(100);