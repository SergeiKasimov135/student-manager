package ru.kasimov.customer.client;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kasimov.customer.entity.FavouriteStudent;

public interface FavouriteStudentsClient {

    Flux<FavouriteStudent> findFavouriteStudents();

    Mono<FavouriteStudent> findFavouriteStudentByStudentId(int studentId);

    Mono<FavouriteStudent> addStudentToFavourites(int studentId);

    Mono<Void> removeStudentFromFavourites(int studentId);

}
