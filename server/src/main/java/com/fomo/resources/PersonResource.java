package com.fomo.resources;

import com.fomo.db.Person;
import com.fomo.db.PersonDao;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
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
    @Path("/{id}")
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvent(@PathParam("id") long id) {
        if (id > 0) {
            return Response.ok(dao.get(id)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/{id}/events")
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEventsFor(@PathParam("id") long id) {
        if (id > 0) {
            return Response.ok(dao.get(id).getEvents()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

//    @GET
//    @Path("/{id}/events")
//    @UnitOfWork
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getGroupsFor(@PathParam("id") long id) {
//        if (id > 0) {
//            return Response.ok(dao.get(id).getGroups()).build();
//        }
//        return Response.status(Response.Status.NOT_FOUND).build();
//    }
}
