package ru.kasimov.customer.repository;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kasimov.customer.entity.FavouriteStudent;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Repository
public class inMemoryFavouriteStudentRepository implements FavouriteStudentRepository {

    private final List<FavouriteStudent> favouriteStudents = Collections.synchronizedList(new LinkedList<>());

    @Override
    public Mono<FavouriteStudent> save(FavouriteStudent favouriteStudent) {
        this.favouriteStudents.add(favouriteStudent);
        return Mono.just(favouriteStudent);
    }

    @Override
    public Mono<Void> deleteByStudentId(int studentId) {
        this.favouriteStudents.removeIf(favouriteStudent -> favouriteStudent.studentId() == studentId);
        return Mono.empty();
    }

    @Override
    public Mono<FavouriteStudent> findByStudentId(int studentId) {
        return Flux.fromIterable(this.favouriteStudents)
                .filter(favouriteStudent -> favouriteStudent.studentId() == studentId)
                .singleOrEmpty();
    }

    @Override
    public Flux<FavouriteStudent> findAll() {
        return Flux.fromIterable(this.favouriteStudents);
    }

}
