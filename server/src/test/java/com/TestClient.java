package com;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.fomo.db.Event;
import com.fomo.db.Group;
import com.fomo.db.Location;
import com.fomo.db.Person;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.filter.LoggingFilter;
import org.joda.time.DateTime;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

public class TestClient {
//    @Test
    public void createEvent() throws IOException {
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

        Response r = client.target("http://fomo-london.rhcloud.com/api/events")
                .request()
                .post(Entity.entity(event, MediaType.APPLICATION_JSON_TYPE));
        System.out.println(IOUtils.toString((InputStream) r.getEntity()));
    }

//    @Test
    public void createGroup() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JodaModule());
        JacksonJsonProvider provider = new JacksonJsonProvider();
        provider.setMapper(objectMapper);
        Client client = ClientBuilder.newClient()
                .register(provider)
                .register(new LoggingFilter(java.util.logging.Logger.getLogger("test"), true))
                .register(JacksonJsonProvider.class);
        Person person1 = new Person("Milan", "Prick");
        Group group = new Group();
        group.setGroupName("test group");
        Person person2 = new Person("Mehdi", "Awesome");
        group.setPeople(ImmutableSet.of(person1, person2));

        Response r = client.target("http://fomo-london.rhcloud.com/api/groups")
                .request()
                .post(Entity.entity(group, MediaType.APPLICATION_JSON_TYPE));
        System.out.println(IOUtils.toString((InputStream) r.getEntity()));
    }
}


