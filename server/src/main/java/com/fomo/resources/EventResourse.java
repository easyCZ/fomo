package com.fomo.resources;

import com.codahale.metrics.annotation.Timed;
import com.fomo.db.Event;
import com.fomo.db.EventDao;
import com.fomo.db.Location;
import com.fomo.db.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.caching.CacheControl;
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
import java.util.List;
import java.util.concurrent.TimeUnit;

@Path("/event")
@Produces(MediaType.APPLICATION_JSON)
public class EventResourse {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventResourse.class);
    private final EventDao eventDao;


    public EventResourse(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @GET
    @Timed(name = "get-requests")
    @CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.DAYS)
    public String sayHello(@QueryParam("name") Optional<String> name) {
        return "hello world";
    }

    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEvent(Event event) {
        if (event != null) {
            eventDao.create(event);
        }
        return Response.ok().build();
    }

    @GET
    @Path("/all")
    @UnitOfWork
    public List<Event> getAllEvents() {
        return eventDao.getAll();
    }


    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JodaModule());
        JacksonJsonProvider provider = new JacksonJsonProvider();
        provider.setMapper(objectMapper);
        Client client = ClientBuilder.newClient()
                .register(provider)
                .register(new LoggingFilter())
                .register(JacksonJsonProvider.class);
        Event event = new Event();
        event.setLocation(new Location("some data"));
        event.setPeople(ImmutableSet.of(new Person("Milan", "Prick"), new Person("Mehdi", "Awesome")));
        event.setStartTime(new DateTime());

        System.out.println(client.target("http://localhost:8080/event")
                .request()
                .post(Entity.entity(event, MediaType.APPLICATION_JSON_TYPE)));
    }
}
