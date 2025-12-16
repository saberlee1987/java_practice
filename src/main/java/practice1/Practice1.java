package practice1;

import java.util.List;
import java.util.Scanner;

public class Practice1 {

    public static void main(String[] args) {
        //test();
        PersonRepository personRepository = new PersonRepository();
        PersonService personService = new PersonService(personRepository);
        startProgram(personService, new Scanner(System.in));
    }

    private static void startProgram(PersonService personService, Scanner scanner) {
        showMenu();
        String choice = scanner.next();
        executeAction(choice, personService, scanner);
    }

    private static void executeAction(String choice, PersonService personService, Scanner scanner) {
        if (null == choice || choice.isEmpty()){
            System.out.println("Invalid choice");
            startProgram(personService, scanner);
            return;
        }
        switch (choice) {
            case "1":
                addInformationPerson(personService,scanner);
                break;
            case "2":
                shoPersons(personService);
                break;
            case "3":
                showPersonById(personService,scanner);
                break;
            case "4":
                updatePersonById(personService,scanner);
                break;
            case "5":
                deletePersonById(personService,scanner);
                break;
            case "6":
                System.out.println("good bye !!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice");
                startProgram(personService, scanner);
        }
        startProgram(personService, scanner);
    }

    private static void deletePersonById(PersonService personService, Scanner scanner) {
        System.out.print("Enter person ID : ");
        long personId = scanner.nextLong();
        try {
            personService.deletePersonById(personId);
            System.out.println("Person has been deleted successfully\n");
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    private static void updatePersonById(PersonService personService, Scanner scanner) {
        System.out.print("Enter person ID : ");
        long personId = scanner.nextLong();
        try {
            Person oldPerson = personService.getPersonById(personId);
            Person newPerson = getPersonInformation(scanner);
            oldPerson.setFirstName(newPerson.getFirstName());
            oldPerson.setLastName(newPerson.getLastName());
            oldPerson.setNationalCode(newPerson.getNationalCode());
            oldPerson.setAge(newPerson.getAge());
            oldPerson.setEmail(newPerson.getEmail());
            oldPerson.setMobile(newPerson.getMobile());
            personService.update(oldPerson);
            System.out.println("Person updated successfully ==> "+oldPerson+"\n");
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    private static void showPersonById(PersonService personService, Scanner scanner) {
        System.out.print("Enter person ID : ");
        long personId = scanner.nextLong();
        try {
            Person person = personService.getPersonById(personId);
            System.out.println("your person information : \n" + person + "\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void shoPersons(PersonService personService) {
        List<Person> persons = personService.getPersons();
        if (persons.isEmpty()) {
            System.out.println("No persons found\n");
        } else {
            personService.getPersons().forEach(person -> {
                System.out.println(person);
                System.out.println("==========================================================");
            });
            System.out.println("\n");
        }
    }

    private static void addInformationPerson(PersonService personService, Scanner scanner) {
        try {
            Person newPerson = getPersonInformation(scanner);
            personService.save(newPerson);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    private static Person getPersonInformation(Scanner scanner) {
        System.out.print("first name: ");
        String firstName = scanner.next();
        System.out.print("last name: ");
        String lastName = scanner.next();
        System.out.print("nationalCode : ");
        String nationalCode = scanner.next();
        System.out.print("email : ");
        String email = scanner.next();
        System.out.print("mobile : ");
        String mobile = scanner.next();
        System.out.print("age : ");
        int age = scanner.nextInt();
        Person person = new Person(firstName, lastName, nationalCode, email, mobile, age);
        return person;
    }

    private static void showMenu() {
        System.out.println("1-enter the new Person information ");
        System.out.println("2-show Persons");
        System.out.println("3-show Person by id");
        System.out.println("4-update Person by id");
        System.out.println("5-delete Person by id");
        System.out.println("6-exit");
        System.out.print("Enter choice : ");
    }


    private static void test() {
        System.out.println("Hello World @@@@");
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

            person3.setAge(72);
            person3.setMobile("09124526687");
            personService.update(person3);


            foundPerson = personService.getPersonById(3);
            System.out.println(foundPerson);

            Boolean b = personService.deletePersonById(3);
            if (b) {
                System.out.println("person has been deleted");
            }
            foundPerson = personService.getPersonById(3);
            System.out.println(foundPerson);

            b = personService.deletePersonById(5);
            if (b) {
                System.out.println("person has been deleted");
            }

            foundPerson = personService.getPersonById(5);
            System.out.println(foundPerson);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}