package com.fomo.builders;

import com.fomo.db.Event;
import com.fomo.db.Person;
import com.fomo.db.Response;
import com.google.common.collect.Sets;

public class ResponseBuilder extends ChildBuilder<PeopleBuilder> {
    private final Response response;

    public ResponseBuilder(PeopleBuilder parent) {
        super(parent);
        this.response = new Response();
    }

    public ResponseBuilder(Event e, Person person) {
        this((PeopleBuilder) null);
        this.response.setEvent(e);
        person.getResponses().add(this.response);
        this.response.setResponder(person);
    }

    public ResponseBuilder going() {
        response.setIsAttending(true);
        return this;
    }

    public ResponseBuilder nahh() {
        response.setIsAttending(false);
        return this;
    }

    public ResponseBuilder message(String msg) {
        response.setMessage(msg);
        return this;
    }

    @Override
    public PeopleBuilder build() {
        if (parentBuilder.person.getResponses() == null) {
            parentBuilder.person.setResponses(Sets.<Response>newHashSet());
        }
        if (parentBuilder.parentBuilder.event.getResponses() == null) {
            parentBuilder.parentBuilder.event.setResponses(Sets.<Response>newHashSet());
        }
        parentBuilder.person.getResponses().add(response);
        response.setResponder(parentBuilder.person);
        parentBuilder.parentBuilder.event.getResponses().add(response);
        response.setEvent(parentBuilder.parentBuilder.event);
        return super.build();
    }

    public Response buildResponse() {
        return this.response;
    }
}
