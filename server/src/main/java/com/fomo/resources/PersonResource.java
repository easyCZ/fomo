package com.fomo.resources;

import com.fomo.db.Person;
import com.fomo.db.dao.PersonDao;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/people")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {
    private final PersonDao dao;

    public PersonResource(PersonDao dao) {
        this.dao = dao;
    }

    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPerson(Person person) {
        if (person != null) {
            dao.create(person);
        }
        return Response.ok().build();
    }

    @GET
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    public Person me(@Context Person me) {
        return me;
    }
}
