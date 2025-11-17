package practice1;

public class Practice1 {

    public static void main(String[] args) {
        System.out.println("Hello World");
        PersonRepository personRepository = new PersonRepository();
        PersonService personService = new PersonService(personRepository);
        Person person1 = new Person("saber", "azizi", "0079028748"
                , "saberazizi66@yahoo.com", "09365627895", 38);

        Person person2 = new Person("bruce", "Lee", "1463492715"
                , "bruce40@yahoo.com", "09351298857", 33);

        Person person3 = new Person("jackie", "chan", "1463493630"
                , "jackie54@yahoo.com", "09360217305", 70);

        try {
            System.out.println(personService.save(person1));
            System.out.println(personService.save(person2));
            System.out.println(personService.save(person3));

            System.out.println("============================================================================================================");
            Person foundPerson;
            foundPerson = personService.getPersonById(1);
            System.out.println(foundPerson);
            foundPerson = personService.getPersonById(2);
            System.out.println(foundPerson);
            foundPerson = personService.getPersonById(3);
            System.out.println(foundPerson);
            foundPerson = personService.getPersonById(5);
            System.out.println(foundPerson);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }


    }
}
