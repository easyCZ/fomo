package com.fomo.resources;

import com.fomo.builders.ResponseBuilder;
import com.fomo.db.Event;
import com.fomo.db.Person;
import com.fomo.db.dao.EventDao;
import com.fomo.db.dao.PersonDao;
import io.dropwizard.hibernate.UnitOfWork;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
public class EventResourse {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventResourse.class);
    private final EventDao eventDao;
    private final PersonDao personDao;

    public EventResourse(EventDao eventDao, PersonDao personDao) {
        this.eventDao = eventDao;
        this.personDao = personDao;
    }

    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Event createEvent(Event event, @Context Person user) {
        return eventDao.create(event);
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public Event getEvent(@PathParam("id") long id) {
        Event event;
        if (id > 0)
            return eventDao.get(id);
        throw new BadRequestException();
    }

    @GET
    @UnitOfWork
    public List<Event> getAllEvents(@Context Person user) {
        return eventDao.getAll(user);
    }

    @POST
    @UnitOfWork
    @Path("/notGoing")
    public void notGoing(Event e, @Context Person user) {
        Event event = eventDao.get(e.getId());
        if (event == null) {
            event = eventDao.get(e.getFbId());
        }
        if (event == null) {
            e.getResponses().add(new ResponseBuilder(e, user).nahh().buildResponse());
            eventDao.create(e);
        }

    }
}
