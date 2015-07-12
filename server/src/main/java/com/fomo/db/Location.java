package com.fomo.db;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class Location {
    @Id @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    public String name;

    public Location() {}
    public Location(String data) {
        this.name = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
