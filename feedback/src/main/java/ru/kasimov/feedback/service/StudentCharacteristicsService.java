package ru.kasimov.feedback.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kasimov.feedback.entity.StudentCharacteristic;

public interface StudentCharacteristicsService {

    Mono<StudentCharacteristic> createStudentCharacteristic(int studentId, int rating, String characteristic);

    Flux<StudentCharacteristic> findStudentCharacteristicsByStudent(int studentId);

}
