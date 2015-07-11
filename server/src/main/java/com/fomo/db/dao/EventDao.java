package com.fomo.db.dao;

import com.fomo.auth.FbUser;
import com.fomo.db.Event;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.ws.rs.NotFoundException;
import java.util.List;

public class EventDao extends AbstractDAO<Event> {

    public EventDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Event create(Event event) {
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
