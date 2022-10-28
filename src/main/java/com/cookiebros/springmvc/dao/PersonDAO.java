package com.cookiebros.springmvc.dao;

import com.cookiebros.springmvc.models.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.cookiebros.springmvc.config.SpringConfig.DRIVER;
import static com.cookiebros.springmvc.config.SpringConfig.URL;
import static com.cookiebros.springmvc.config.SpringConfig.USERNAME;
import static com.cookiebros.springmvc.config.SpringConfig.PASSWORD;

@Component
public class PersonDAO {

    private static int PEOPLE_COUNT;


    private static Connection connection;

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Person> index() {
        List<Person> people = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM Person";
            ResultSet rs = statement.executeQuery(SQL);

            while (rs.next()) {
                Person person = new Person();
                person.setId(rs.getInt("id"));
                person.setName(rs.getString("name"));
                person.setSurname(rs.getString("surname"));
                person.setAge(rs.getByte("age"));
                person.setEmail(rs.getString("email"));
                people.add(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return people;
    }

    public Person show(int id) {
        Person person = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM Person WHERE id=?");
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            person = new Person();
            person.setId(rs.getInt("id"));
            person.setName(rs.getString("name"));
            person.setSurname(rs.getString("surname"));
            person.setAge((byte) rs.getInt("age"));
            person.setEmail(rs.getString("email"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return person;
    }

    public void save(Person savedPerson) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Person(name, surmane, age, email) VALUES(?, ?, ?, ?)");
            preparedStatement.setString(1, savedPerson.getName());
            preparedStatement.setString(2, savedPerson.getSurname());
            preparedStatement.setInt(3, savedPerson.getAge());
            preparedStatement.setString(4, savedPerson.getEmail());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void update(int id, Person updatedPerson) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE Person SET name=?, surname=?, age=?, email=? WHERE id=?");
            preparedStatement.setString(1, updatedPerson.getName());
            preparedStatement.setString(2, updatedPerson.getSurname());
            preparedStatement.setInt(3, updatedPerson.getAge());
            preparedStatement.setString(4, updatedPerson.getEmail());
            preparedStatement.setInt(5, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM Person WHERE id=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}