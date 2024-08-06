package ru.kasimov.registry.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.kasimov.registry.controller.payload.NewStudentPayload;
import ru.kasimov.registry.entity.Student;
import ru.kasimov.registry.service.StudentService;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("registry-api/students")
public class StudentsRestController {
    private final StudentService studentService;

    @GetMapping
    public Iterable<Student> findStudents(@RequestParam(name = "filter", required = false) String filter) {
        return this.studentService.findAllStudents(filter);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createStudent(@Validated @RequestBody NewStudentPayload payload,
                                           BindingResult bindingResult,
                                           UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Student student = this.studentService.createStudent(payload.name(), payload.lastName(), payload.age(), payload.email(), payload.phoneNumber(), payload.courseLevel());

            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("studentrest-api/students/{studentId}")
                            .build(Map.of("studentId", student.getId())))
                    .body(student);
        }
    }
}
