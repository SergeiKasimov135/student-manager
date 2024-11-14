package ru.kasimov.customer.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kasimov.customer.entity.StudentCharacteristic;

public interface StudentCharacteristicsRepository {

    Mono<StudentCharacteristic> save(StudentCharacteristic studentCharacteristic);

    Flux<StudentCharacteristic> findAllByStudentId(int studentId);

}
