package com.cookiebros.springmvc.dao;

import com.cookiebros.springmvc.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("SELECT p FROM Person p", Person.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Person show(int id) {
        Session session = sessionFactory.getCurrentSession();

        return  session.get(Person.class, id);
    }

    //Валидация email, запрос к БД, существует ли Person с таким email
    public Optional<Person> show(String email) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Person WHERE email = ?", Person.class).;
    }

    public void save(Person savedPerson) {
    }

    public void update(int id, Person updatedPerson) {
    }

    public void delete(int id) {
    }
}