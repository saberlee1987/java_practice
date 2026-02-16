package practice1;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Person {
    private Long id;
    private String firstName;
    private String lastName;
    private String nationalCode;
    private String email;
    private String mobile;
    private Integer age;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updatedAt;
    private String persianCreateDate;
    private String persianUpdateDate;

    public Person() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Person(Long id, String firstName, String lastName, String nationalCode
            , String email, String mobile, Integer age) {
        this();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.email = email;
        this.mobile = mobile;
        this.age = age;
    }

    public Person(String firstName, String lastName, String nationalCode, String email, String mobile, Integer age) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.email = email;
        this.mobile = mobile;
        this.age = age;
    }
}
