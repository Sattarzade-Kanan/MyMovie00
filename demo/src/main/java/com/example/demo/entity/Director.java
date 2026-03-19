package com.example.demo.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "directors")
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String sur_name;

    @Column(nullable = false)
    private String birthday;

    @Column(nullable = false)
    private Integer age;

    @OneToMany(mappedBy = "director")
    private List<Movie> movies;

        public Director(){

        }

    public Director(Integer id, String name, String sur_name, String birthday, Integer age, List<Movie> movies) {
        this.id = id;
        this.name = name;
        this.sur_name = sur_name;
        this.birthday = birthday;
        this.age = age;
        this.movies = movies;
    }

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

    public String getSur_name() {
        return sur_name;
    }

    public void setSur_name(String sur_name) {
        this.sur_name = sur_name;
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

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
