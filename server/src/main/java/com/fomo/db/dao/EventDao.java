package com.fomo.db.dao;

import com.fomo.auth.FbUser;
import com.fomo.builders.PeopleBuilder;
import com.fomo.db.Event;
import com.fomo.db.Person;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.ws.rs.NotFoundException;
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
                Person persistedPerson = personDao.get(person.getFbId());
                if (persistedPerson == null) {
                    persistedPerson = personDao.create(person);
                }
                persistedPeople.add(persistedPerson);
            }
        }
        event.setPeople(persistedPeople);
        return persist(event);
    }

    public List<Event> getAll(FbUser user) {
        return list(currentSession().createQuery("from Event"));
    }

    public Event get(long id) {
        Event e = super.get(Long.valueOf(id));
        if (e == null) throw new NotFoundException("Resource does not exist");
        return e;
    }
}
