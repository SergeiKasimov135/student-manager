package ru.kasimov.manager.repository;

import ru.kasimov.manager.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    List<Student> findAll();

    Student save(Student student);

    Student saveOrUpdate(Student student);

    Optional<Student> findById(Integer id);

    void deleteById(Integer id);
}
