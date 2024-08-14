package ru.kasimov.manager.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kasimov.manager.client.BadRequestException;
import ru.kasimov.manager.client.StudentRestClient;
import ru.kasimov.manager.controller.payload.EditStudentPayload;
import ru.kasimov.manager.entity.Student;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("registry/students/{studentId:\\d+}")
public class StudentController {
    private final StudentRestClient studentRestClient;

    private final MessageSource messageSource;

    @ModelAttribute("student")
    public Student student(@PathVariable("studentId") int studentId) {
        return this.studentRestClient.findStudent(studentId).orElseThrow(
                () -> new NoSuchElementException("student.errors.not_found")
        );
    }

    @GetMapping
    public String getStudent() {
        return "registry/students/student";
    }

    @GetMapping("edit")
    public String getEditStudentPage() {
        return "registry/students/edit";
    }

    @PostMapping("edit")
    public String editStudent(@ModelAttribute(value = "student", binding = false) Student student,
                              EditStudentPayload payload, Model model) {
        try {
            this.studentRestClient.editStudent(
                    student.id(), payload.name(), payload.lastName(), payload.age(),
                    payload.email(), payload.phoneNumber(), payload.courseLevel()
            );
            return "redirect:/registry/students/%d".formatted(student.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "/registry/students/edit";
        }
    }

    @PostMapping("delete")
    public String deleteStudent(@ModelAttribute("student") Student student) {
        this.studentRestClient.deleteStudent(student.id());
        return "redirect:/registry/students/list";
    }

    @ExceptionHandler
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
