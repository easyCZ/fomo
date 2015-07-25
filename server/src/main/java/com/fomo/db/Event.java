package com.fomo.db;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fomo.builders.EventBuilder;
import com.google.common.collect.Lists;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class Event {
    @Id @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private DateTime startTime;
    @Column
    private DateTime endTime;
    // we may want to make this a @ManyToOne, but that can wait..
    @JoinColumn @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Location location;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name="event_to_people",
            joinColumns =        @JoinColumn(name = "event_id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "person_id", nullable = false, updatable = false))
    @Fetch(FetchMode.SELECT)
    private Set<Person> people;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

    public static EventBuilder create() {
        return new EventBuilder();
    }

    @JsonIgnore
    public List<Person> getPeopleSortedByName() {
        TreeSet<Person> sorted = new TreeSet<>(personComparator);
        sorted.addAll(getPeople());
        return Lists.newArrayList(sorted);
    }

    @JsonIgnore
    public List<Response> getResponsesSortedByResponderName() {
        TreeSet<Response> sorted = new TreeSet<>(responseComparator);
        sorted.addAll(getResponses());
        return Lists.newArrayList(sorted);
    }

    private static final Comparator<Response> responseComparator = new Comparator<Response>() {
        @Override
        public int compare(Response o1, Response o2) {
            return o1.getResponder().getName().compareTo(o2.getResponder().getName());
        }
    };

    private static final Comparator<Person> personComparator = new Comparator<Person>() {
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };
}
