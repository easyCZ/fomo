package com.fomo.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.fomo.db.*;
import com.google.common.collect.ImmutableSet;
import io.dropwizard.hibernate.UnitOfWork;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.filter.LoggingFilter;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

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
    public Response createEvent(Event event) {
        if (event != null) {
            return Response.ok(eventDao.create(event)).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvent(@PathParam("id") long id) {
        if (id > 0) {
            return Response.ok(eventDao.get(id)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/all")
    @UnitOfWork
    public List<Event> getAllEvents() {
        return eventDao.getAll();
    }


    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JodaModule());
        JacksonJsonProvider provider = new JacksonJsonProvider();
        provider.setMapper(objectMapper);
        Client client = ClientBuilder.newClient()
                .register(provider)
                .register(new LoggingFilter(java.util.logging.Logger.getLogger("test"), true))
                .register(JacksonJsonProvider.class);
        Event event = new Event();
        event.setLocation(new Location("some data"));
        Person person1 = new Person("Milan", "Prick");
        Group group = new Group();
        group.setGroupName("test group");
        Person person2 = new Person("Mehdi", "Awesome");
        group.setPeople(ImmutableSet.of(person1, person2));
        event.setPeople(ImmutableSet.of(person1, person2));
        event.setStartTime(new DateTime());

        Response r = client.target("http://localhost:8080/api/events")
                .request()
                .post(Entity.entity(event, MediaType.APPLICATION_JSON_TYPE));
        System.out.println(IOUtils.toString((InputStream) r.getEntity()));
    }
}
