package com.cookiebros.springmvc.dao;

import com.cookiebros.springmvc.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Component
public class PersonDAO {

    private static int PEOPLE_COUNT;

    public final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> index() {
//        Если поля модели и столбцы таблицы не совпадают, используем собственный  PersonMapper implements RowMapper<Person>
//        return jdbcTemplate.query("SELECT * FROM Person", new PersonMapper());
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class)).
                stream().findAny().orElse(null);
    }

    public void save(Person savedPerson) {
        jdbcTemplate.update("INSERT INTO Person(name, surname, age, email) VALUES(?, ?, ?, ?)",
                savedPerson.getName(),
                savedPerson.getSurname(),
                savedPerson.getAge(),
                savedPerson.getEmail());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET name=?, surname=?, age=?, email=? WHERE id=?",
                updatedPerson.getName(),
                updatedPerson.getSurname(),
                updatedPerson.getAge(),
                updatedPerson.getEmail(),
                id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }


    ////////test batch upate
//    public void testBatchUpdate() {
//        List<Person> people = create1000people();
//        jdbcTemplate.batchUpdate("INSERT INTO People VALUES(?, ?, ?, ?, ?)", new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps, int i) throws SQLException {
//                ps.setInt(1, people.get(i).getId());
//                ps.setString(1, people.get(i).getName());
//                ps.setString(1, people.get(i).getSurname());
//                ps.setInt(1, people.get(i).getAge());
//                ps.setString(1, people.get(i).getEmail());
//            }
//            @Override
//            public int getBatchSize() {
//                return people.size();
//            }
//        });
//    }
//
//    public List<Person> create1000people() {
//        List<Person> people = new ArrayList<>();
//        for (int i=0; i<1000; i++) {
//            people.add(new Person(i, "Name"+i, "Surname"+i, (byte) 30, "email"+i+"@gmail.com"));
//        }
//        return people;
//    }
}