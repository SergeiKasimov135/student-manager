package ru.kasimov.manager.client;

import ru.kasimov.manager.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRestClient {
    List<Student> findAllStudents(String filter);

    Student createStudent(String name, String lastName,
                          Integer age, String email,
                          String phoneNumber, int courseLevel);

    Optional<Student> findStudent(Integer studentId);

    void editStudent(Integer studentId, String name, String lastName,
                     Integer age, String email,
                     String phoneNumber, int courseLevel);

    void deleteStudent(Integer studentId);
}
