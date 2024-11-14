package ru.kasimov.customer.client;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kasimov.customer.entity.Student;

@RequiredArgsConstructor
public class WebClientStudentsClient implements StudentsClient {

    private final WebClient webClient;

    @Override
    public Flux<Student> findAllStudents(String filter) {
        return this.webClient.get()
                .uri("/registry-api/students?filter={filter}", filter)
                .retrieve()
                .bodyToFlux(Student.class);
    }

    @Override
    public Mono<Student> findStudent(int id) {
        return this.webClient.get()
                .uri("/registry-api/{studentId}", id)
                .retrieve()
                .bodyToMono(Student.class)
                .onErrorComplete(WebClientResponseException.class);
    }
}
