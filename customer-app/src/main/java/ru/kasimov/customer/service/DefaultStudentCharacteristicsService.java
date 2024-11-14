package ru.kasimov.customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kasimov.customer.entity.StudentCharacteristic;
import ru.kasimov.customer.repository.StudentCharacteristicsRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultStudentCharacteristicsService implements StudentCharacteristicsService {

    private final StudentCharacteristicsRepository studentCharacteristicsRepository;

    @Override
    public Mono<StudentCharacteristic> createStudentCharacteristic(int studentId, int rating, String review) {
        return this.studentCharacteristicsRepository.save(
                new StudentCharacteristic(UUID.randomUUID(), studentId, rating, review)
        );
    }

    @Override
    public Flux<StudentCharacteristic> findStudentCharacteristicByStudent(int studentId) {
        return this.studentCharacteristicsRepository.findAllByStudentId(studentId);
    }
}
