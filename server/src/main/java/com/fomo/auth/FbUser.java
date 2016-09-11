package com.fomo.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fomo.builders.PeopleBuilder;
import com.fomo.db.Person;

@JsonIgnoreProperties(ignoreUnknown = true)
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

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getGender() {
        return gender;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getLink() {
        return link;
    }

    public String getLocale() {
        return locale;
    }

    public String getName() {
        return name;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public String getVerified() {
        return verified;
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
