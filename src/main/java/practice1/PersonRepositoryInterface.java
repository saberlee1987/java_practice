package practice1;

import java.util.List;

public interface PersonRepositoryInterface {
    Person save(Person person);
    void updatePerson(Person newPerson);
    Person getPersonById(long id);
    void deletePersonById(long id);
    List<Person> getPersons();
}