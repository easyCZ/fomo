package com.fomo.db.dao;

import com.fomo.db.Event;
import com.fomo.db.Person;
import com.fomo.db.Response;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventDao extends AbstractDAO<Event> {
    private final PersonDao personDao;

    public EventDao(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.personDao = new PersonDao(sessionFactory);
    }

    public Event create(Event event) {
        Set<Person> persistedPeople = new HashSet<>();
        for (Person person : event.getPeople()) {
            if (person.getId() == 0) {
                Person persistedPerson = personDao.get(person.getId());
                if (persistedPerson == null) {
                    persistedPerson = personDao.create(person);
                }
                persistedPeople.add(persistedPerson);
            }
        }
        event.setPeople(persistedPeople);
        return persist(event);
    }

    public List<Event> getAll(Person user) {
        List<Event> events = list(currentSession().createQuery("from Event"));
        for (Event e : events) {
            Hibernate.initialize(e.getResponses());
        }
        return events;
    }

    public Event get(long id) {
        Event e = super.get(Long.valueOf(id));
        return e;
    }

    public Event get(String fbId) {
        Query query = currentSession().createQuery("from Event e where e.fbId = :fbId");
        return super.uniqueResult(query.setParameter("fbId", fbId));
    }
}
