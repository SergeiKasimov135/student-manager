package ru.kasimov.customer.client;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kasimov.customer.entity.StudentCharacteristic;

public interface StudentCharacteristicsClient {

    Flux<StudentCharacteristic> findStudentByStudentId(int studentId);

    Mono<StudentCharacteristic> createStudentCharacteristic(Integer studentId, Integer rating, String characteristic);

}
