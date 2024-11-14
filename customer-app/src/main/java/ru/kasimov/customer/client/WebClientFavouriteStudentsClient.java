package ru.kasimov.customer.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kasimov.customer.client.exeption.ClientBadRequestException;
import ru.kasimov.customer.client.payload.NewFavouriteStudentPayload;
import ru.kasimov.customer.entity.FavouriteStudent;

import java.util.List;

@RequiredArgsConstructor
public class WebClientFavouriteStudentsClient implements FavouriteStudentsClient {

    private final WebClient webClient;

    @Override
    public Flux<FavouriteStudent> findFavouriteStudents() {
        return this.webClient
                .get()
                .uri("/feedback-api/favourite-students")
                .retrieve()
                .bodyToFlux(FavouriteStudent.class);
    }

    @Override
    public Mono<FavouriteStudent> findFavouriteStudentByStudentId(int studentId) {
        return this.webClient
                .get()
                .uri("/feedback-api/favourite-students/by-student-id/{studentId}", studentId)
                .retrieve()
                .bodyToMono(FavouriteStudent.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }

    @Override
    public Mono<FavouriteStudent> addStudentToFavourites(int studentId) {
        return this.webClient
                .post()
                .uri("/feedback-api/favourite-students")
                .bodyValue(new NewFavouriteStudentPayload(studentId))
                .retrieve()
                .bodyToMono(FavouriteStudent.class)
                .onErrorMap(WebClientResponseException.NotFound.class,
                        exception -> new ClientBadRequestException(exception,
                                ((List<String>) exception.getResponseBodyAs(ProblemDetail.class)
                                        .getProperties().get("errors"))));
    }

    @Override
    public Mono<Void> removeStudentFromFavourites(int studentId) {
        return this.webClient
                .delete()
                .uri("/feedback/favourite-students/by-student-id/{studentId}", studentId)
                .retrieve()
                .toBodilessEntity()
                .then();
    }

}
