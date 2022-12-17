CREATE TABLE IF NOT EXISTS ABONENTS (
    student_id int,
    student_name varchar(255)
);

CREATE TABLE IF NOT EXISTS BOOKS (
    book_id int,
    book_title varchar(255),
    student_id int
);

TRUNCATE TABLE ABONENTS;

TRUNCATE TABLE BOOKS;