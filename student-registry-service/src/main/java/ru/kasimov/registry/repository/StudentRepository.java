package ru.kasimov.registry.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.kasimov.registry.entity.Student;

public interface StudentRepository extends CrudRepository<Student, Integer> {
    @Query(name = "Student.findAllByNameLikeIgnoringCase")
    Iterable<Student> findAllByNameLikeIgnoreCase(@Param("filter") String filter);
}
