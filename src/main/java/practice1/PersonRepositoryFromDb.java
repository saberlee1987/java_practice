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
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/test2"
                , "saber66", "AdminSaber66");
    }

    @Override
    public Person save(Person person) {
        try (Connection connection = getConnection();
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
            throw new RuntimeException(e);
        }
        return person;
    }

    @Override
    public void updatePerson(Person newPerson) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("""
                update persons set firstName=?, lastName=?, mobile=?, nationalCode=?,
                          age=? ,email=? ,updatedAt=?  where id=?
            """);
        ) {
            preparedStatement.setString(1, newPerson.getFirstName());
            preparedStatement.setString(2, newPerson.getLastName());
            preparedStatement.setString(3, newPerson.getMobile());
            preparedStatement.setString(4, newPerson.getNationalCode());
            preparedStatement.setInt(5, newPerson.getAge());
            preparedStatement.setString(6, newPerson.getEmail());
            preparedStatement.setObject(7, newPerson.getUpdatedAt());
            preparedStatement.setLong(8, newPerson.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Person getPersonById(long id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from persons where id = ?");
        ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getPersonFromDb(resultSet);
            } else {
                throw new RuntimeException("No person found with id " + id);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePersonById(long id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from persons where id = ?");
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Person> getPersons() {
        List<Person> persons = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from persons");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                persons.add(getPersonFromDb(resultSet));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
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
        person.setEmail(resultSet.getString("email"));
        person.setCreatedAt(LocalDateTime.ofInstant(resultSet.getTimestamp("createdAt").toInstant(), ZoneId.of("Asia/Tehran")));
        person.setUpdatedAt(LocalDateTime.ofInstant(resultSet.getTimestamp("updatedAt").toInstant(), ZoneId.of("Asia/Tehran")));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        person.setPersianCreateDate(PersianDate.from(person.getCreatedAt()).atTime(LocalTime.from(person.getCreatedAt())).format(formatter));
        person.setPersianUpdateDate(PersianDate.from(person.getUpdatedAt()).atTime(LocalTime.from(person.getUpdatedAt())).format(formatter));

        return person;
    }
}
