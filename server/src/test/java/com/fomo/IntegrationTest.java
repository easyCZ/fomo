package com.fomo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.fomo.db.*;
import com.fomo.db.Response;
import com.google.common.collect.Lists;
import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.junit.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IntegrationTest {
    private static final String CONFIG_PATH = ResourceHelpers.resourceFilePath("test-example.yml");

    @ClassRule
    public static final DropwizardAppRule<Config> RULE = new DropwizardAppRule<>(
            FomoApp.class, CONFIG_PATH, ConfigOverride.config("database.url", "jdbc:h2:" + createTempFile()));

    private Client client;

    @BeforeClass
    public static void migrateDb() throws Exception {
//        RULE.getApplication().run("db", "migrate", CONFIG_PATH);
    }

    private static String createTempFile() {
        try {
            return File.createTempFile("test-example", null).getAbsolutePath();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Before
    public void setUp() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JodaModule());
        JacksonJsonProvider provider = new JacksonJsonProvider();
        provider.setMapper(objectMapper);
        client = ClientBuilder.newClient()
                              .register(provider);

    }

    @Test
    public void createEvent() throws IOException {
        Event event = Event.create()
                            .at(new Location("some data"))
                            .on(new DateTime())
                            .with()
                                .name("Mehdi")
                                .withResponse()
                                    .going()
                                    .message("WHOOOO")
                                    .build()
                                .build()
                            .build();

        post(event);

        Event fromDb = getFromDb(Event.class, 1l);
        assertTrue(event.getStartTime().equals(fromDb.getStartTime()));
        assertEquals(event.getPeople().size(), 1);
        Person person = Lists.newArrayList(fromDb.getPeople()).get(0);
        assertEquals(person.getName(), "Mehdi");
        assertEquals(fromDb.getResponses().size(), 1);
        Response response = Lists.newArrayList(fromDb.getResponses()).get(0);
        assertEquals(response.getMessage(), "WHOOOO");
        assertTrue(response.getIsAttending());
    }

    private Event getFromDb(Class clazz, Serializable id) {
        return (Event) getSessionFactory().openSession().get(clazz, id);
    }

    private void post(Event event) {
        javax.ws.rs.core.Response r = client.target("http://localhost:" + RULE.getLocalPort() + "/api/events")
                                .request()
                                .post(Entity.entity(event, MediaType.APPLICATION_JSON_TYPE));
        assertEquals(r.getStatus(), javax.ws.rs.core.Response.Status.OK.getStatusCode());
    }

    private SessionFactory getSessionFactory() {
        return ((FomoApp) RULE.getApplication()).hibernateBundle.getSessionFactory();
    }

    @Test
    public void createGroup() throws IOException {
        Event event = Event.create()
                            .at(new Location("some data"))
                            .on(new DateTime())
                            .with()
                                .name("Mehdi")
                                .withResponse()
                                    .going()
                                    .message("WHOOOO")
                                    .build()
                                .build()
                            .with()
                                .name("Milan")
                                .withResponse()
                                    .nahh()
                                    .message("I'm boring")
                                    .build()
                                .build()
                            .build();
        post(event);
        Event fromDb = getFromDb(Event.class, 2l);
        assertTrue(event.getStartTime().equals(fromDb.getStartTime()));
        assertEquals(event.getPeople().size(), 2);
        List<Person> peopleList = fromDb.getPeopleSortedByName();
        assertEquals(peopleList.get(0).getName(), "Mehdi");
        assertEquals(peopleList.get(1).getName(), "Milan");
        assertEquals(fromDb.getResponses().size(), 2);
        List<Response> responses = fromDb.getResponsesSortedByResponderName();
        assertEquals(responses.get(0).getMessage(), "WHOOOO");
        assertEquals(responses.get(1).getMessage(), "I'm boring");
    }
}


