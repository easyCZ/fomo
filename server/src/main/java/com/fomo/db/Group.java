package com.fomo.db;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator=JSOGGenerator.class)
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @ManyToMany
    @JoinTable(name="group_to_people",
            joinColumns =        @JoinColumn(name = "group_id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "person_id", nullable = false, updatable = false))
    private Set<Person> people;
    @Column
    private String groupName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
