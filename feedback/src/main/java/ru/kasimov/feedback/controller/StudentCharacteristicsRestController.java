package ru.kasimov.feedback.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kasimov.feedback.controller.payload.NewStudentCharacteristicPayload;
import ru.kasimov.feedback.entity.StudentCharacteristic;
import ru.kasimov.feedback.service.StudentCharacteristicsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("feedback-api/student-characteristic")
public class StudentCharacteristicsRestController {

    private final StudentCharacteristicsService studentCharacteristicsService;

    @GetMapping("by-student-id/{studentId}:\\d+")
    public Flux<StudentCharacteristic> findStudentCharacteristicByStudent(@PathVariable("studentId") int studentId) {
        return this.studentCharacteristicsService.findStudentCharacteristicsByStudent(studentId);
    }

    @PostMapping
    public Mono<ResponseEntity<StudentCharacteristic>> createStudentCharacteristic(
            @Valid @RequestBody Mono<NewStudentCharacteristicPayload> payloadMono,
            UriComponentsBuilder uriComponentsBuilder) {
        return payloadMono
                .flatMap(payload -> this.studentCharacteristicsService.createStudentCharacteristic(
                        payload.studentId(), payload.rating(), payload.characteristic())
                )
                .map(studentCharacteristic -> ResponseEntity
                        .created(uriComponentsBuilder.replacePath("feedback-api/student-characteristic/{id}")
                                .build(studentCharacteristic.getId()))
                        .body(studentCharacteristic));
    }

}
