package com.example.helloworld.db;

import javax.persistence.*;

@Entity
public class Response {
    @Column @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
