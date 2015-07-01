package com.fomo.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class PersonDao extends AbstractDAO<Person> {
    public PersonDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Person create(Person person) {
        return persist(person);
    }
    public Person get(long id) {
        return super.get(Long.valueOf(id));
    }
}
