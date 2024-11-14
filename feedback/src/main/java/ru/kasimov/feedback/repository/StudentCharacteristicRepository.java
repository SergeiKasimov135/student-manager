package ru.kasimov.feedback.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.kasimov.feedback.entity.StudentCharacteristic;

import java.util.UUID;

public interface StudentCharacteristicRepository extends ReactiveMongoRepository<StudentCharacteristic, UUID> {

    @Query("{studentId: ?0}")
    Flux<StudentCharacteristic> findAllByStudentId(int studentId);

}
