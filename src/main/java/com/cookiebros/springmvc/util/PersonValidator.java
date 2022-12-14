package com.cookiebros.springmvc.util;

import com.cookiebros.springmvc.models.Person;
import com.cookiebros.springmvc.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        //name
        int nameMin = 2;
        int nameMax = 30;
        String namePattern = "[A-Z]\\w{1,30}";

        if (person.getName().length() < nameMin || person.getName().length() > nameMax) {
            errors.rejectValue("name", "", "Name should be between 2 and 30 characters");
        } else if (!Pattern.compile(namePattern).matcher(person.getName()).matches()) {
            errors.rejectValue("name", "", "Invalid characters in name");
        } // Добавить проверку на разные языки в имени


        //surname
        int surnameMin = 2;
        int surnameMax = 40;
        String surnamePattern = "[A-Z]\\w{1,40}";
        if (person.getSurname().length() < surnameMin || person.getSurname().length() > surnameMax) {
            errors.rejectValue("surname", "", "Surname should be between 2 and 30 characters");
        } else if (!Pattern.compile(surnamePattern).matcher(person.getSurname()).matches()) {
            errors.rejectValue("surname", "", "Invalid characters in surname");
        } // Добавить проверку на разные языки в фамилии

        //age
        if (person.getAge() < 14 || person.getAge() > 120)
            errors.rejectValue("age", "", "Age should be greater then 14");

        //email
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!Pattern.compile(emailPattern).matcher(person.getEmail()).matches())
            errors.rejectValue("email", "", "Email should be valid");

        //проверка на уникальность email в БД
        Person foundPerson = peopleService.findByEmail(person.getEmail()).orElse(null);

        if (foundPerson != null && foundPerson.getId() != person.getId())
            errors.rejectValue("email", "", "This email is already taken");
    }
}
