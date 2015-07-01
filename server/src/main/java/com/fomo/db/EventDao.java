package com.fomo.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class EventDao extends AbstractDAO<Event> {

    public EventDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Event create(Event event) {
        return persist(event);
    }

    public List<Event> getAll() {
        return list(namedQuery("Event.findAll"));
    }

    public Event get(long id) {
        return super.get(Long.valueOf(id));
    }
}
