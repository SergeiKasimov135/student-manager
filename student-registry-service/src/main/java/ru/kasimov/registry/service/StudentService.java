package ru.kasimov.registry.service;

import ru.kasimov.registry.entity.Student;

import java.util.Optional;

public interface StudentService {
    Iterable<Student> findAllStudents(String filter);

    Student createStudent(String name, String lastName,
                          Integer age, String email,
                          String phoneNumber, int courseLevel);

    Optional<Student> findStudent(Integer studentId);

    void editStudent(Integer StudentId, String name, String lastName,
                     Integer age, String email,
                     String phoneNumber, int courseLevel);

    void deleteStudent(Integer studentId);
}
