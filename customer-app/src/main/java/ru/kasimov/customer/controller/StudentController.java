package ru.kasimov.customer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.kasimov.customer.client.StudentsClient;
import ru.kasimov.customer.client.exeption.ClientBadRequestException;
import ru.kasimov.customer.client.payload.NewStudentCharacteristicPayload;
import ru.kasimov.customer.entity.Student;
import ru.kasimov.customer.service.FavouriteStudentService;
import ru.kasimov.customer.service.StudentCharacteristicsService;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer/students/{studentId:\\d+}")
@Slf4j
public class StudentController {

    private final StudentsClient studentsClient;

    private final FavouriteStudentService favouriteStudentsService;

    private final StudentCharacteristicsService studentCharacteristicService;

    @ModelAttribute(name = "student", binding = false)
    public Mono<Student> loadStudent(@PathVariable("studentId") int id) {
        return this.studentsClient.findStudent(id)
                .switchIfEmpty(Mono.error(new NoSuchElementException("customer.student.error.not_found")));
    }

    @GetMapping
    public Mono<String> getStudentPage(@PathVariable("studentId") int id, Model model) {
        model.addAttribute("inFavourite", false);
        return this.studentCharacteristicService.findStudentCharacteristicByStudent(id)
                .collectList()
                .doOnNext(studentCharacteristic -> model.addAttribute("characteristics", studentCharacteristic))
                .then(this.favouriteStudentsService.findFavouriteStudentByStudent(id)
                        .doOnNext(_ -> model.addAttribute("inFavourite", true)))
                .thenReturn("customer/students/student");
    }

    @PostMapping("add-to-favourites")
    public Mono<String> addStudentToFavourites(@PathVariable("student") Mono<Student> studentMono) {
        return studentMono.map(Student::id)
                .flatMap(studentId -> this.favouriteStudentsService.addStudentToFavourites(studentId)
                        .thenReturn("redirect:/customer/students/%d".formatted(studentId))
                        .onErrorResume(exception -> {
                            log.error(exception.getMessage(), exception);
                            return Mono.just("redirect:/customer/students/%d".formatted(studentId));
                        }));
    }

    @PostMapping("remove-from-favourites")
    public Mono<String> removeStudentFromFavourites(@PathVariable("student") Mono<Student> studentMono) {
        return studentMono.map(Student::id)
                .flatMap(studentId -> this.favouriteStudentsService.removeStudentFromFavourites(studentId)
                        .thenReturn("redirect:/customer/students/%d".formatted(studentId)));
    }

    @PostMapping("create-characteristic")
    public Mono<String> createCharacteristic(@PathVariable("studentId") int id,
                                             NewStudentCharacteristicPayload payload,
                                             Model model) {
        return this.studentCharacteristicService
                .createStudentCharacteristic(id, payload.rating(), payload.characteristic())
                .thenReturn("redirect:/customer/students/%d".formatted(id))
                .onErrorResume(ClientBadRequestException.class, exception -> {
                    model.addAttribute("inFavourite", false);
                    model.addAttribute("payload", payload);
                    model.addAttribute("errors", exception.getErrors());
                    return this.favouriteStudentsService.findFavouriteStudentByStudent(id)
                            .doOnNext(_ -> model.addAttribute("inFavourite", true))
                            .thenReturn("customer/students/student");
                });
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model) {
        model.addAttribute("error", exception.getMessage());
        return "errors/404";
    }
}
