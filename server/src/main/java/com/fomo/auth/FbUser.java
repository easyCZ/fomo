package com.fomo.auth;

import com.fomo.builders.PeopleBuilder;
import com.fomo.db.Person;

public final class FbUser {
    private String id;
    private String email;
    private String first_name;
    private String gender;
    private String last_name;
    private String link;
    private String locale;
    private String name;
    private String timezone;
    private String updated_time;
    private String verified;

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "FbUser{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", gender='" + gender + '\'' +
                ", last_name='" + last_name + '\'' +
                ", link='" + link + '\'' +
                ", locale='" + locale + '\'' +
                ", name='" + name + '\'' +
                ", timezone='" + timezone + '\'' +
                ", updated_time='" + updated_time + '\'' +
                ", verified='" + verified + '\'' +
                '}';
    }

    public Person toPerson() {
        return new PeopleBuilder()
                        .name(name)
                        .fbId(id)
                        .buildPerson();
    }
}
