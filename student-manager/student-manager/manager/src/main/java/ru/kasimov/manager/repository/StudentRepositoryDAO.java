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

    @Override
    public List<Student> findAll() {
        List<Student> students;

        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            students = session.createQuery("FROM Student", Student.class).getResultList();
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            session.getTransaction().rollback();
            throw exception;
        }
        return students;
    }

    @Override
    public Student save(Student student) {
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            session.save(student);
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            session.getTransaction().rollback();
            throw exception;
        }

        return student;
    }

    @Override
    public Student saveOrUpdate(Student student) {
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            session.saveOrUpdate(student);
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            session.getTransaction().rollback();
            throw exception;
        }

        return student;
    }

    @Override
    public Optional<Student> findById(Integer id) {
        Session session = sessionFactory.getCurrentSession();

        Student student;
        try {
            session.beginTransaction();
            student = session.get(Student.class, id);
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            session.getTransaction().rollback();
            throw exception;
        }

        return Optional.ofNullable(student);
    }

    @Override
    public void deleteById(Integer id) {
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            Student student = session.get(Student.class, id);
            if (student != null) {
                session.delete(student);
                session.getTransaction().commit();
            }
        } catch (HibernateException exception) {
            session.getTransaction().rollback();
            throw exception;
        }
    }
}
