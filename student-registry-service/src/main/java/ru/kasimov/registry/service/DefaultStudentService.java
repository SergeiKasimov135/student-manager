package ru.kasimov.registry.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kasimov.registry.entity.Student;
import ru.kasimov.registry.repository.StudentRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultStudentService implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public Iterable<Student> findAllStudents(String filter) {
        if (filter != null && !filter.isBlank()) {
            return this.studentRepository.findAllByNameLikeIgnoreCase("%" + filter + "%");
        } else {
            return this.studentRepository.findAll();
        }
    }

    @Override
    @Transactional
    public Student createStudent(String name, String lastName, Integer age,
                                 String email, String phoneNumber, int courseLevel) {
        return this.studentRepository.save(
                new Student(null, name, lastName, age, email, phoneNumber, courseLevel)
        );
    }

    @Override
    public Optional<Student> findStudent(Integer studentId) {
        return this.studentRepository.findById(studentId);
    }

    @Override
    @Transactional
    public void editStudent(Integer studentId, String name,
                            String lastName, Integer age, String email,
                            String phoneNumber, int courseLevel) {

        Optional<Student> optionalStudent = this.studentRepository.findById(studentId);

        optionalStudent.ifPresentOrElse(student -> {
            updateStudentDetails(student, name, lastName,
                                 age, email, phoneNumber, courseLevel);
        }, () -> {
            throw new NoSuchElementException();
        });
    }

    private void updateStudentDetails(Student student, String name,
                                      String lastName, Integer age, String email,
                                      String phoneNumber, int courseLevel) {
        student.setName(name);
        student.setLastName(lastName);
        student.setAge(age);
        student.setEmail(email);
        student.setPhoneNumber(phoneNumber);
        student.setCourseLevel(courseLevel);
    }

    @Override
    @Transactional
    public void deleteStudent(Integer studentId) {
        this.studentRepository.deleteById(studentId);
    }
}
