package ru.kasimov.customer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;
import ru.kasimov.customer.client.StudentsClient;
import ru.kasimov.customer.entity.FavouriteStudent;
import ru.kasimov.customer.service.FavouriteStudentService;

@Controller
@RequiredArgsConstructor
@RequestMapping("customer/students")
public class StudentsController {

    private final StudentsClient studentsClient;

    private final FavouriteStudentService favouriteStudentService;

    @GetMapping("list")
    public Mono<String> getStudentsListPage(Model model,
                                            @RequestParam(name="filter", required = false) String filter) {
        model.addAttribute("filter", filter);
        return this.studentsClient.findAllStudents(filter)
                .collectList()
                .doOnNext(students -> model.addAttribute("students", students))
                .thenReturn("customer/students/list");
    }

    @PostMapping("favourites")
    public Mono<String> getFavouriteStudentsPage(Model model,
                                                 @RequestParam(name = "filter", required = false) String filter) {
        model.addAttribute("filter", filter);
        return this.favouriteStudentService.findFavouritesStudents()
                .map(FavouriteStudent::studentId)
                .collectList()
                .flatMap(favouriteStudents -> this.studentsClient.findAllStudents(filter)
                        .filter(student -> favouriteStudents.contains(student.id()))
                        .collectList()
                        .doOnNext(students -> model.addAttribute("students", students)))
                .thenReturn("customer/students/favourites");
    }

}
