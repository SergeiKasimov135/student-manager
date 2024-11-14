package ru.kasimov.customer.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kasimov.customer.entity.StudentCharacteristic;

public interface StudentCharacteristicsService {

    Mono<StudentCharacteristic> createStudentCharacteristic(int studentId, int rating, String review);

    Flux<StudentCharacteristic> findStudentCharacteristicByStudent(int studentId);

}

