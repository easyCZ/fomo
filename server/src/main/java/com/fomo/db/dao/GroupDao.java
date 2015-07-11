package com.fomo.db.dao;

import com.fomo.db.Group;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class GroupDao extends AbstractDAO<Group> {
    public GroupDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    public Group create(Group group) {
        return persist(group);
    }
    public Group get(long id) {
        return super.get(Long.valueOf(id));
    }
}
