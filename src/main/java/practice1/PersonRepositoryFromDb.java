package practice1;

import com.github.mfathi91.time.PersianDate;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PersonRepositoryFromDb implements PersonRepositoryInterface {


    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Person save(Person person) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test2"
                , "saber66", "AdminSaber66");
             PreparedStatement preparedStatement = connection.prepareStatement("""
                     insert into persons (age, email, firstName, lastName, mobile, nationalCode, createdAt, updatedAt) VALUES (?,?,?,?,?,?,?,?)
                     """, Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setInt(1, person.getAge());
            preparedStatement.setString(2, person.getEmail());
            preparedStatement.setString(3, person.getFirstName());
            preparedStatement.setString(4, person.getLastName());
            preparedStatement.setString(5, person.getMobile());
            preparedStatement.setString(6, person.getNationalCode());
            preparedStatement.setObject(7,person.getCreatedAt());
            preparedStatement.setObject(8,person.getUpdatedAt());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                person.setId(generatedKeys.getLong(1));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return person;
    }

    @Override
    public void updatePerson(Person newPerson) {

    }

    @Override
    public Person getPersonById(long id) {
        return null;
    }

    @Override
    public void deletePersonById(long id) {

    }

    @Override
    public List<Person> getPersons() {
        List<Person> persons = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test2"
                , "saber66", "AdminSaber66");
             PreparedStatement preparedStatement = connection.prepareStatement("select * from persons");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                persons.add(getPersonFromDb(resultSet));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return persons;
    }

    private Person getPersonFromDb(ResultSet resultSet) throws Exception {
        Person person = new Person();
        person.setId(resultSet.getLong("id"));
        person.setFirstName(resultSet.getString("firstName"));
        person.setLastName(resultSet.getString("lastName"));
        person.setAge(resultSet.getInt("age"));
        person.setNationalCode(resultSet.getString("nationalCode"));
        person.setMobile(resultSet.getString("mobile"));
        person.setCreatedAt(LocalDateTime.ofInstant(resultSet.getTimestamp("createdAt").toInstant(), ZoneId.of("Asia/Tehran")));
        person.setUpdatedAt(LocalDateTime.ofInstant(resultSet.getTimestamp("updatedAt").toInstant(), ZoneId.of("Asia/Tehran")));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        person.setPersianCreateDate(PersianDate.from(person.getCreatedAt()).atTime(LocalTime.from(person.getCreatedAt())).format(formatter));
        person.setPersianUpdateDate(PersianDate.from(person.getUpdatedAt()).atTime(LocalTime.from(person.getUpdatedAt())).format(formatter));

        return person;
    }
}
