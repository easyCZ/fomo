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
        test.setName("Mehdi");
        test.setFbId("10100878053552372");

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
                .cookie("fbAuth", "CAAMlg3wzdhUBAAlk0R7NXCsT9zNRG1In3CmrNBli1ZCronhVXDPitcnrlNIA1XBfH508s973cGYneUowUKItMBuh1gJbzwX9LilwMmIbykQ6SeMKxeQT9H0OfxygDuZB3v9GwIZCygdYZCdLZAHtRG5MoMGDI2OZCI7SY6RHidJocDyPtEYpuszwSGPgGaY1dFfm711kr4cMPoNngnYwNZAluouDxyEeeMZD")
                .post(Entity.entity(test, MediaType.APPLICATION_JSON_TYPE));
        System.out.println(r.getStatus());
        System.out.println(IOUtils.toString((InputStream) r.getEntity()));
    }
}
