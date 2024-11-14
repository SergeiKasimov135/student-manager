package ru.kasimov.feedback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kasimov.feedback.entity.StudentCharacteristic;
import ru.kasimov.feedback.repository.StudentCharacteristicRepository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DefaultStudentCharacteristicsService implements StudentCharacteristicsService {

    private final StudentCharacteristicRepository studentCharacteristicRepository;

    @Override
    public Mono<StudentCharacteristic> createStudentCharacteristic(int studentId, int rating, String characteristic) {
        return this.studentCharacteristicRepository.save(
                new StudentCharacteristic(UUID.randomUUID(), studentId, rating, characteristic)
        );
    }

    @Override
    public Flux<StudentCharacteristic> findStudentCharacteristicsByStudent(int studentId) {
        return this.studentCharacteristicRepository.findAllByStudentId(studentId);
    }

}
