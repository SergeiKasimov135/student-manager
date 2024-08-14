package ru.kasimov.manager.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.kasimov.manager.controller.payload.EditStudentPayload;
import ru.kasimov.manager.controller.payload.NewStudentPayload;
import ru.kasimov.manager.entity.Student;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class RestClientStudentsRestClient implements StudentRestClient {

    private static final ParameterizedTypeReference<List<Student>> STUDENTS_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {
            };

    private final RestClient restClient;

    @Override
    public List<Student> findAllStudents(String filter) {
        return this.restClient
                .get()
                .uri("registry-api/students?filter={filter}", filter)
                .retrieve()
                .body(STUDENTS_TYPE_REFERENCE);
    }

    @Override
    public Student createStudent(String name, String lastName, Integer age,
                                 String email, String phoneNumber, int courseLevel) {
        try {
            return this.restClient
                    .post()
                    .uri("/registry-api/students")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewStudentPayload(name, lastName, age, email, phoneNumber, courseLevel))
                    .retrieve()
                    .body(Student.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<Student> findStudent(Integer studentId) {
        try {
            return Optional.ofNullable(
                    this.restClient
                            .get()
                            .uri("/registry-api/students/{studentId}", studentId)
                            .retrieve()
                            .body(Student.class)
            );
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void editStudent(Integer studentId, String name, String lastName, Integer age, String email, String phoneNumber, int courseLevel) {
        try {
            this.restClient
                    .patch()
                    .uri("/registry-api/students/{studentId}", studentId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new EditStudentPayload(name, lastName, age, email, phoneNumber, courseLevel))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteStudent(Integer studentId) {
        try {
            this.restClient
                    .delete()
                    .uri("registry-api/students/{studentId}", studentId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
