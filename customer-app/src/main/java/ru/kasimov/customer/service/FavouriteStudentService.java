package ru.kasimov.customer.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kasimov.customer.entity.FavouriteStudent;

public interface FavouriteStudentService {

    Mono<FavouriteStudent> addStudentToFavourites(int studentId);

    Mono<Void> removeStudentFromFavourites(int studentId);

    Mono<FavouriteStudent> findFavouriteStudentByStudent(int studentId);

    Flux<FavouriteStudent> findFavouritesStudents();

}
