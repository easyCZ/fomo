package com.fomo.db;

import javax.persistence.*;

@Entity
public class Location {
    @Id @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    public String somedata;

    public Location() {}
    public Location(String data) {
        this.somedata = data;
    }

    public String getSomedata() {
        return somedata;
    }

    public void setSomedata(String somedata) {
        this.somedata = somedata;
    }
}
