package com.fomo.builders;

import com.fomo.db.Event;
import com.fomo.db.Person;
import com.google.common.collect.Sets;

public class PeopleBuilder extends ChildBuilder<EventBuilder> {
    final Person person;
    public PeopleBuilder(EventBuilder parentBuilder) {
        this(parentBuilder, new Person());
    }

    public PeopleBuilder(EventBuilder parentBuilder, Person person) {
        super(parentBuilder);
        if (this.parentBuilder.event.getPeople() == null) {
            this.parentBuilder.event.setPeople(Sets.<Person>newHashSet());
        }
        this.person = person;
    }

    public PeopleBuilder name(String name) {
        this.person.setName(name);
        return this;
    }

    public ResponseBuilder withResponse() {
        return new ResponseBuilder(this);
    }

    @Override
    public EventBuilder build() {
        parentBuilder.event.getPeople().add(person);
        if (person.getEvents() == null) {
            person.setEvents(Sets.<Event>newHashSet());
        }
        person.getEvents().add(parentBuilder.event);
        return super.build();
    }
}
