package ru.kasimov.customer.repository;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kasimov.customer.entity.StudentCharacteristic;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Repository
public class InMemoryStudentCharacteristicsRepository implements StudentCharacteristicsRepository {

    private final List<StudentCharacteristic> studentCharacteristics = Collections.synchronizedList(new LinkedList<>());

    @Override
    public Mono<StudentCharacteristic> save(StudentCharacteristic studentCharacteristic) {
        this.studentCharacteristics.add(studentCharacteristic);
        return Mono.just(studentCharacteristic);
    }

    @Override
    public Flux<StudentCharacteristic> findAllByStudentId(int studentId) {
        return Flux.fromIterable(this.studentCharacteristics)
                .filter(studentCharacteristic -> studentCharacteristic.studentId() == studentId);
    }
}
