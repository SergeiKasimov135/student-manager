package ru.kasimov.feedback.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kasimov.feedback.controller.payload.NewFavouriteStudentPayload;
import ru.kasimov.feedback.entity.FavouriteStudent;
import ru.kasimov.feedback.service.FavouriteStudentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("feedback-api/favourite-students")
public class FavouriteStudentsRestController {

    private final FavouriteStudentService favouriteStudentService;

    @GetMapping
    public Flux<FavouriteStudent> findFavouriteStudents() {
        return this.favouriteStudentService.findFavouritesStudents();
    }

    @GetMapping("by-student-id/{studentId:\\d+}")
    public Mono<FavouriteStudent> findFavouriteStudentByStudentId(@PathVariable("studentId") int studentId) {
        return this.favouriteStudentService.findFavouriteStudentByStudent(studentId);
    }

    @PostMapping
    public Mono<ResponseEntity<FavouriteStudent>> addStudentToFavourites(
            @Valid @RequestBody Mono<NewFavouriteStudentPayload> payloadMono,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        return payloadMono
                .flatMap(payload -> this.favouriteStudentService.addStudentToFavourites(payload.studentId()))
                .map(favouriteStudent -> ResponseEntity
                        .created(uriComponentsBuilder.replacePath("feedback-api/favourite-student/{id}")
                                .build(favouriteStudent.getId()))
                        .body(favouriteStudent));
    }

    @DeleteMapping("by-student-id/{studentId:\\d+}")
    public Mono<ResponseEntity<Void>> removeStudentFromFavourites(@PathVariable("studentId") int studentId) {
        return this.favouriteStudentService.removeStudentFromFavourites(studentId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

}
