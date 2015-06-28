package com.example.helloworld.db;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "com.example.helloworld.db.Event.findAll",
                query = "SELECT e FROM Event e"
        )
})
public class Event {
    @Id @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private DateTime startTime;
    @Column
    private DateTime endTime;
    @JoinColumn @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Location location;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="event_to_people", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
    @Fetch(FetchMode.SELECT)
    private Set<Person> people;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="event_to_group", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    @Fetch(FetchMode.SELECT)
    private Set<Group> groups;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="event_to_response", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "response_id"))
    @Fetch(FetchMode.SELECT)
    private Set<Response> responses;
    @Column
    private String title;
    @Column
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }

    public Set<Response> getResponses() {
        return responses;
    }

    public void setResponses(Set<Response> responses) {
        this.responses = responses;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
