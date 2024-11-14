package ru.kasimov.feedback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kasimov.feedback.entity.FavouriteStudent;
import ru.kasimov.feedback.repository.FavouriteStudentRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultFavouriteStudentService implements FavouriteStudentService {

    private final FavouriteStudentRepository favouriteStudentRepository;

    @Override
    public Mono<FavouriteStudent> addStudentToFavourites(int studentId) {
        return this.favouriteStudentRepository.save(new FavouriteStudent(UUID.randomUUID(), studentId));
    }

    @Override
    public Mono<Void> removeStudentFromFavourites(int studentId) {
        return this.favouriteStudentRepository.deleteByStudentId(studentId);
    }

    @Override
    public Mono<FavouriteStudent> findFavouriteStudentByStudent(int studentId) {
        return this.favouriteStudentRepository.findByStudentId(studentId);
    }

    @Override
    public Flux<FavouriteStudent> findFavouritesStudents() {
        return this.favouriteStudentRepository.findAll();
    }

}
