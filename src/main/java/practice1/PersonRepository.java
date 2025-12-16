package practice1;

import java.util.ArrayList;
import java.util.List;

public class PersonRepository {
    private final List<Person> persons;
    private volatile long nextId;

    public PersonRepository() {
        nextId = 0;
        persons = new ArrayList<>();
    }

    public synchronized long getNextId() {
        return ++nextId;
    }

    public Person save(Person person) {
        person.setId(getNextId());
        persons.add(person);
        System.out.println("insert person to repository: id " + person.getId());
        return person;
    }

    public void updatePerson(Person newPerson) {
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).getId().equals(newPerson.getId())) {
                persons.set(i, newPerson);
                break;
            }
        }
    }

    public List<Person> getPersons() {
        return persons;
    }

    public Person getPersonById(long id) {
        Person foundPerson = null;
        for (Person person : persons) {
            if (person.getId() == id) {
                foundPerson = person;
                break;
            }
        }
        return foundPerson;
    }

    public void deletePersonById(long id) {
        int foundIndex = foundIndexPersonById(id);
        if (foundIndex >= 0) {
            persons.remove(foundIndex);
        }
    }

    private int foundIndexPersonById(long id) {
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }
}