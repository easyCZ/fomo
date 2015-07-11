package com;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.fomo.db.Person;
import com.fomo.db.Response;
import com.google.common.collect.Sets;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class HitLocalTest {
    @Test
    public void main() throws IOException {
        Person test = new Person();
        test.setResponses(Sets.<Response>newHashSet());
        Response response = new Response();
        response.setResponder(test);
        test.getResponses().add(response);

        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JodaModule())
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
        JacksonJsonProvider provider = new JacksonJsonProvider();
        provider.setMapper(objectMapper);
        Client client = ClientBuilder.newClient()
                                    .register(new LoggingFilter(Logger.getLogger("test"), true))
                                    .register(provider);
        javax.ws.rs.core.Response r = client.target("http://localhost:8080/api/people")
                .request()
                .post(Entity.entity(test, MediaType.APPLICATION_JSON_TYPE));
        System.out.println(r.getStatus());
        System.out.println(IOUtils.toString((InputStream) r.getEntity()));
    }
}
