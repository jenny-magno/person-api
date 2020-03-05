package com.jnmagno.restservice.person;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="persons")
public class PersonEntity {

    private Long id;

    @NotEmpty(message = "Please provide a first name")
	private String firstName;

    @NotEmpty(message = "Please provide a last name")
    private String lastName;

    @NotNull(message = "Please provide an age")
    private Integer age;
	private List<String> hobby;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "age", nullable = false)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(name = "hobby", nullable = true)
    @ElementCollection(targetClass=String.class)
    public List<String> getHobby() {
        return hobby;
    }

    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }

    public String toFormattedString() {
        return String.format("{\"firstName\": \"%s\", \"lastName\": \"%s\", \"age\": \"%d\", \"hobby\": [\"%s\"]}",
         this.firstName, this.lastName, this.age, String.join("\",\"", this.hobby));
    }

}