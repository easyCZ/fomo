package com.fomo.resources;

import com.fomo.db.Event;
import com.fomo.db.EventDao;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
public class EventResourse {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventResourse.class);
    private final EventDao eventDao;

    public EventResourse(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Event createEvent(Event event) {
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
    public List<Event> getAllEvents() {
        return eventDao.getAll();
    }

}
