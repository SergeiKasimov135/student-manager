package ru.kasimov.manager.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;
import ru.kasimov.manager.entity.Student;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepositoryDAO implements StudentRepository {

    private final SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Student.class)
            .buildSessionFactory();

    @FunctionalInterface
    private interface SessionOperation<T> {
        T execute(Session session);
    }

    private <T> T executeTransaction(SessionOperation<T> sessionOperation) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            T result = sessionOperation.execute(session);
            session.getTransaction().commit();

            return result;
        } catch (HibernateException exception) {
            session.getTransaction().rollback();
            throw exception;
        }

    }

    @Override
    public List<Student> findAll() {
        return executeTransaction(session ->
                session.createQuery("FROM Student", Student.class).getResultList()
        );
    }

    @Override
    public Student save(Student student) {
        return executeTransaction(session -> {
            session.save(student);
            return student;
        });
    }

    @Override
    public Student saveOrUpdate(Student student) {
        return executeTransaction(session -> {
            session.saveOrUpdate(student);
            return student;
        });
    }

    @Override
    public Optional<Student> findById(Integer id) {
        return executeTransaction(session -> Optional.ofNullable(
                session.get(Student.class, id)
        ));
    }

    @Override
    public void deleteById(Integer id) {
        executeTransaction(session -> {
            Student student = session.get(Student.class, id);
            if (student != null)
                session.delete(student);
            return null;
        });
    }
}
