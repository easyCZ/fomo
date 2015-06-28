package com.example.helloworld.db;

import javax.persistence.*;
import java.util.Set;

@Entity
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
}
