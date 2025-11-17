package practice1;

import java.util.Objects;

public class Person {
    private Long id;
    private String firstName;
    private String lastName;
    private String nationalCode;
    private String email;
    private String mobile;
    private Integer age;

    public Person() {
    }

    public Person(Long id, String firstName, String lastName, String nationalCode
            , String email, String mobile, Integer age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.email = email;
        this.mobile = mobile;
        this.age = age;
    }

    public Person(String firstName, String lastName, String nationalCode, String email, String mobile, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.email = email;
        this.mobile = mobile;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(firstName, person.firstName)
                && Objects.equals(lastName, person.lastName) && Objects.equals(nationalCode, person.nationalCode)
                && Objects.equals(email, person.email) && Objects.equals(mobile, person.mobile) && Objects.equals(age, person.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, nationalCode, email, mobile, age);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nationalCode='" + nationalCode + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", age=" + age +
                '}';
    }
}
