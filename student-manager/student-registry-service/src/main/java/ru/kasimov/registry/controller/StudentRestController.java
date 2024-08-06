package ru.kasimov.registry.controller;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kasimov.registry.controller.payload.EditStudentPayload;
import ru.kasimov.registry.entity.Student;
import ru.kasimov.registry.service.StudentService;

import java.util.Locale;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("registry-api/students/{studentId:\\d+}")
public class StudentRestController {
    private final StudentService studentService;

    private final MessageSource messageSource;

    @ModelAttribute("student")
    public Student student(@PathVariable("studentId") int studentId) {
        return this.studentService.findStudent(studentId).orElseThrow(
                () -> new NoSuchElementException("student.errors.not_found")
        );
    }

    @GetMapping
    public Student findStudent(@ModelAttribute("student") Student student) {
        return student;
    }

    @PatchMapping
    public ResponseEntity<?> editStudent(@PathVariable("studentId") int studentId,
                                         @Validated @RequestBody EditStudentPayload payload,
                                         BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            this.studentService.editStudent(studentId, payload.name(), payload.lastName(), payload.age(),
                    payload.email(), payload.phoneNumber(), payload.courseLevel());

            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteStudent(@PathVariable("studentId") int studentId) {
        this.studentService.deleteStudent(studentId);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception, Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.NOT_FOUND,
                        this.messageSource.getMessage(exception.getMessage(),
                                                      new Object[0],
                                                      exception.getMessage(),
                                                      locale)
                )
        );
    }
}
