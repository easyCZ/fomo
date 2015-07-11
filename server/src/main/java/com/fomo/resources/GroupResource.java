package com.fomo.resources;

import com.fomo.db.Group;
import com.fomo.db.dao.GroupDao;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/groups")
@Produces(MediaType.APPLICATION_JSON)
public class GroupResource {
    private final GroupDao dao;

    public GroupResource(GroupDao dao) {
        this.dao = dao;
    }

    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGroup(Group group) {
        if (group != null) {
            dao.create(group);
        }
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGroup(@PathParam("id") long id) {
        if (id > 0) {
            return Response.ok(dao.get(id)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
