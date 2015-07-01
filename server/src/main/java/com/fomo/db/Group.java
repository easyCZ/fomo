package com.fomo.db;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIdentityInfo(generator=JSOGGenerator.class)
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @ManyToMany
    @JoinTable(name="group_to_people", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
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
