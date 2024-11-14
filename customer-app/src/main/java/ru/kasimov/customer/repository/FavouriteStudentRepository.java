package ru.kasimov.customer.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kasimov.customer.entity.FavouriteStudent;

public interface FavouriteStudentRepository {

    Mono<FavouriteStudent> save(FavouriteStudent favouriteStudent);

    Mono<Void> deleteByStudentId(int studentId);

    Mono<FavouriteStudent> findByStudentId(int studentId);

    Flux<FavouriteStudent> findAll();

}
