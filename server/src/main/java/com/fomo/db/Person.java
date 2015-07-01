package com.fomo.db;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator=JSOGGenerator.class)
public class Person {
    @Id @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private String title;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "people")
    private Set<Event> events;
//    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "people")
//    private Set<Group> groups;

    public Person() {}
    public Person(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

//    public Set<Group> getGroups() {
//        return groups;
//    }
//
//    public void setGroups(Set<Group> groups) {
//        this.groups = groups;
//    }
}
