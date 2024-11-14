package ru.kasimov.customer.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kasimov.customer.client.exeption.ClientBadRequestException;
import ru.kasimov.customer.client.payload.NewStudentCharacteristicPayload;
import ru.kasimov.customer.entity.StudentCharacteristic;

import java.util.List;

@RequiredArgsConstructor
public class WebClientStudentCharacteristicsClient implements StudentCharacteristicsClient {

    private final WebClient webClient;

    @Override
    public Flux<StudentCharacteristic> findStudentByStudentId(int studentId) {
        return webClient
                .get()
                .uri("/feedback-api/student-characteristic/by-student-id/{studentId}", studentId)
                .retrieve()
                .bodyToFlux(StudentCharacteristic.class);
    }

    @Override
    public Mono<StudentCharacteristic> createStudentCharacteristic(Integer studentId, Integer rating, String characteristic) {
        return this.webClient
                .post()
                .uri("/feedback-api/student-characteristic")
                .bodyValue(new NewStudentCharacteristicPayload(studentId, rating, characteristic))
                .retrieve()
                .bodyToMono(StudentCharacteristic.class)
                .onErrorMap(WebClientResponseException.class,
                        exception -> new ClientBadRequestException(exception,
                                (List<String>) exception.getResponseBodyAs(ProblemDetail.class)
                                        .getProperties().get("errors")));
    }
}
