package ru.kasimov.manager.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kasimov.manager.controller.payload.EditStudentPayload;
import ru.kasimov.manager.entity.CourseLevel;
import ru.kasimov.manager.entity.Student;
import ru.kasimov.manager.service.StudentService;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("students/{studentId:\\d+}")
public class StudentController {
    private final StudentService studentService;

    private final MessageSource messageSource;

    @ModelAttribute("student")
    public Student student(@PathVariable("studentId") int studentId) {
        return this.studentService.findStudent(studentId).orElseThrow(
                () -> new NoSuchElementException("student.errors.not_found")
        );
    }

    @GetMapping
    public String getStudent() {
        return "students/student";
    }

    @GetMapping("edit")
    public String getStudentEditPage(@ModelAttribute("student") Student student, Model model) {
        return "students/edit";
    }

    @PostMapping("edit")
    public String editStudent(@ModelAttribute(value = "student", binding = false) Student student,
                              @Validated EditStudentPayload payload, BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());

            return "students/edit";
        } else {
            CourseLevel courseLevel = CourseLevel.values()[payload.courseLevel() - 1];
            this.studentService.editStudent(
                    student.getId(), payload.name(), payload.lastName(), payload.age(),
                    payload.email(), payload.phoneNumber(), courseLevel
            );

            return "redirect:/students/%d".formatted(student.getId());
        }
    }

    @PostMapping("delete")
    public String deleteStudent(@ModelAttribute("student") Student student) {
        this.studentService.deleteStudent(student.getId());
        return "redirect:/students/list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model,
                                               HttpServletResponse response, Locale locale) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute(
                "error",
                this.messageSource.getMessage(exception.getMessage(), new Object[0], locale)
        );
        return "errors/404";
    }
}
