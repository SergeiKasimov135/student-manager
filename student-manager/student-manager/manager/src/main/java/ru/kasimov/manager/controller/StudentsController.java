package ru.kasimov.manager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kasimov.manager.controller.payload.NewStudentPayload;
import ru.kasimov.manager.entity.CourseLevel;
import ru.kasimov.manager.entity.Student;
import ru.kasimov.manager.service.StudentService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentsController {

    private final StudentService studentService;

    @GetMapping("list")
    public String getListOfStudents(Model model) {
        model.addAttribute("students", this.studentService.findAllStudents());
        return "students/list";
    }

    @GetMapping("create")
    public String getNewProductPage() {
        return "students/new_student";
    }

    @PostMapping("create")
    public String createStudent(@Validated NewStudentPayload payload,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());

            return "students/new_student";
        } else {
            CourseLevel courseLevel = CourseLevel.values()[payload.courseLevel() - 1];
            Student student = this.studentService.createStudent(
                    payload.name(), payload.lastName(), payload.age(),
                    payload.email(), payload.phoneNumber(), courseLevel
            );

            return "redirect:/students/%d".formatted(student.getId());
        }
    }
}
