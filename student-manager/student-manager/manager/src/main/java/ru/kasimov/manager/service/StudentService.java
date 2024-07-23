package ru.kasimov.manager.service;

import ru.kasimov.manager.entity.CourseLevel;
import ru.kasimov.manager.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> findAllStudents();

    Student createStudent(String name, String lastName,
                          Integer age, String email,
                          String phoneNumber, CourseLevel courseLevel);

    Optional<Student> findStudent(Integer studentId);

    void editStudent(Integer StudentId, String name, String lastName,
                     Integer age, String email,
                     String phoneNumber, CourseLevel courseLevel);

    void deleteStudent(Integer studentId);
}
