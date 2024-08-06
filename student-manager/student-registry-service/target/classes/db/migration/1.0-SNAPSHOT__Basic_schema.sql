CREATE SCHEMA IF NOT EXISTS registry;

CREATE TABLE registry.t_student (
    id             serial PRIMARY KEY,
    c_name         varchar(20) NOT NULL CHECK (length(trim(c_name)) >= 2),
    c_last_name    varchar(20) NOT NULL CHECK (length(trim(c_last_name)) >= 2),
    c_age          int CHECK (c_age >= 17 AND c_age <= 45),
    c_email        varchar(255) CHECK ( c_email ~ '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$' ),
    c_phone_number varchar(11) CHECK (length(trim(c_phone_number)) = 11 AND c_phone_number ~ '^\d{11}$'),
    c_course_level int CHECK (c_course_level >= 1 AND c_course_level <= 4)
);
