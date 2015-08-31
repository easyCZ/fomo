package com.fomo.db.dao;

import com.fomo.auth.FbUser;
import com.fomo.db.Person;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import static com.google.common.base.Preconditions.checkNotNull;

public class PersonDao extends AbstractDAO<Person> {
    public PersonDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Person create(Person person) {
        return persist(person);
    }

    public Person create(Session session, Person person) {
        session.saveOrUpdate(checkNotNull(person));
        return person;
    }

    public Person get(long id) {
        return super.get(id);
    }

    public Person create(FbUser fbUser) {
        return persist(fbUser.toPerson());
    }
    public Person get(FbUser fbUser) {
        return get(currentSession(), fbUser.getId());
    }
    public Person get(Session session, String fbId) {
        Query query = session.createQuery("from Person p where p.fbId = :fbId");
        return super.uniqueResult(query.setParameter("fbId", fbId));
    }
}
