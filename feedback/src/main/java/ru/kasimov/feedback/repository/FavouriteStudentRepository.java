package ru.kasimov.feedback.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import ru.kasimov.feedback.entity.FavouriteStudent;

import java.util.UUID;

public interface FavouriteStudentRepository extends ReactiveCrudRepository<FavouriteStudent, UUID> {

    Mono<Void> deleteByStudentId(int studentId);

    Mono<FavouriteStudent> findByStudentId(int studentId);

}
