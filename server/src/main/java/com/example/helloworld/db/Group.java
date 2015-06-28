package com.example.helloworld.db;

import javax.persistence.*;
import java.util.List;

@Entity
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @ManyToMany
    @JoinTable(name="group_to_people", joinColumns = @JoinColumn(name = "gropu_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> people;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }
}
