package com.example.demo.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DirectorForm {

    private Integer id;
    @NotBlank(message = "Name is Required!")
    @Size(min = 5,max = 25, message = "The name must be between 3 and 25 characters long.")
    private String name;

    @NotBlank(message = "SurName is Required!")
    @Size(min = 5,max = 25, message = "The surName must be between 5 and 25 characters long.")
    private String surname;

    @NotBlank(message = "Birthday is Required!")
    private String birthday;

    @NotBlank(message = "Age is Required!")
    private Integer age;


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
