package com.cookiebros.springmvc.dao;

import com.cookiebros.spring.models.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private List<Person> people;
    private static int PEOPLE_COUNT;

    {
        people = new ArrayList<>();
        people.add(new Person(++PEOPLE_COUNT, "Tom", "Aaa", (byte) 20, "tom@gmail.com"));
        people.add(new Person(++PEOPLE_COUNT, "Bob", "Bbb", (byte) 21,"bob@gmail.com"));
        people.add(new Person(++PEOPLE_COUNT, "John", "Ccc", (byte) 22,"john@gmail.com"));
        people.add(new Person(++PEOPLE_COUNT, "Sarah", "Ddd", (byte) 23,"sarah@gmail.com"));
        people.add(new Person(++PEOPLE_COUNT, "Leo", "Eee", (byte) 24,"leo@gmail.com"));
    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id) {
        return people.stream().filter(person -> id == person.getId()).findAny().orElse(null);
    }


    public void save(Person savedPerson) {
        savedPerson.setId(++PEOPLE_COUNT);
        people.add(savedPerson);
    }

    public void update(int id, Person updatedPerson) {
        Person person = show(id);
        person.setName(updatedPerson.getName());
        person.setSurname(updatedPerson.getSurname());
        person.setAge(updatedPerson.getAge());
        person.setEmail(updatedPerson.getEmail());
    }

    public void delete(int id) {
        people.removeIf(person -> id == person.getId());
    }
}