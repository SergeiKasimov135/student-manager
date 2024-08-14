package ru.kasimov.manager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kasimov.manager.client.BadRequestException;
import ru.kasimov.manager.client.StudentRestClient;
import ru.kasimov.manager.controller.payload.NewStudentPayload;
import ru.kasimov.manager.entity.Student;

@Controller
@RequiredArgsConstructor
@RequestMapping("registry/students")
public class StudentsController {
    private final StudentRestClient studentRestClient;

    @GetMapping("list")
    public String getListOfStudents(Model model,
                                    @RequestParam(value = "filter", required = false) String filter) {
        model.addAttribute("students", this.studentRestClient.findAllStudents(filter));
        model.addAttribute("filter", filter);

        return "registry/students/list";
    }

    @GetMapping("create")
    public String getNewStudentPage() {
        return "registry/students/new_student";
    }

    @PostMapping("create")
    public String createStudent(NewStudentPayload payload, Model model) {
        try {
            Student student = this.studentRestClient.createStudent(
                    payload.name(), payload.lastName(), payload.age(),
                    payload.email(), payload.phoneNumber(), payload.courseLevel()
            );

            return "redirect:/registry/students/%d".formatted(student.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());

            return "registry/students/new_student";
        }
    }
}
