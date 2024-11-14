package ru.kasimov.customer.client;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kasimov.customer.entity.Student;

public interface StudentsClient {

    Flux<Student> findAllStudents(String filter);

    Mono<Student> findStudent(int id);

}
