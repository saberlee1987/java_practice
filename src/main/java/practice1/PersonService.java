package practice1;

import java.util.List;

public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public synchronized Person save(Person person) {
        checkDuplicatePerson(person, null);
        return personRepository.save(person);
    }

    public synchronized void update(Person newPerson) {
        getPersonById(newPerson.getId());
        checkDuplicatePerson(newPerson, newPerson.getId());
        personRepository.updatePerson(newPerson);
    }

    private void checkDuplicatePerson(Person person, Long currentId) {
        boolean result = false;
        List<Person> persons = personRepository.getPersons();
        if (!persons.isEmpty()) {
            persons = persons.stream()
                    .filter(p -> p.getNationalCode().equals(person.getNationalCode()))
                    .toList();
            if (currentId != null) {
                persons = persons.stream().filter(p -> !p.getId().equals(currentId)).toList();
            }
            result = !persons.isEmpty();
        }
        if (result) {
            throw new RuntimeException(String.format("Person with nationalCode %s already exists",  person.getNationalCode()));
        }
    }

    public Person getPersonById(long id) {
        Person foundPerson = personRepository.getPersonById(id);
        if (foundPerson == null) {
            throw new RuntimeException(String.format("Person by id = %d not found", id));
        }
        return foundPerson;
    }
}
