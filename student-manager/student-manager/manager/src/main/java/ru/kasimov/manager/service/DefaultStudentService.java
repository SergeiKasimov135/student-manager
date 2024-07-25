package ru.kasimov.manager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kasimov.manager.entity.CourseLevel;
import ru.kasimov.manager.entity.Student;
import ru.kasimov.manager.repository.StudentRepository;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultStudentService implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAllStudents() {
        return this.studentRepository.findAll();
    }

    @Override
    @Transactional
    public Student createStudent(String name, String lastName, Integer age,
                                 String email, String phoneNumber, CourseLevel courseLevel) {
        return this.studentRepository.save(
                new Student(name, lastName, age, email, phoneNumber, courseLevel)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> findStudent(Integer studentId) {
        return this.studentRepository.findById(studentId);
    }

    @Override
    @Transactional
    public void editStudent(Integer studentId, String name,
                            String lastName, Integer age, String email,
                            String phoneNumber, CourseLevel courseLevel) {

        Optional<Student> optionalStudent = this.studentRepository.findById(studentId);

        optionalStudent.ifPresentOrElse(student -> {
            updateStudentDetails(student, name, lastName,
                                 age, email, phoneNumber, courseLevel);
            this.studentRepository.saveOrUpdate(student);
        }, () -> {
                    throw new NoSuchElementException();
        });
    }

    private void updateStudentDetails(Student student, String name,
                                      String lastName, Integer age, String email,
                                      String phoneNumber, CourseLevel courseLevel) {
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
