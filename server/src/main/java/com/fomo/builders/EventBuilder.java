package com.fomo.builders;

import com.fomo.db.Event;
import com.fomo.db.Location;
import org.joda.time.DateTime;

public class EventBuilder {
    final Event event;

    public EventBuilder() {
        this(new Event());
    }

    public EventBuilder(Event event) {
        this.event = event;
    }

    public EventBuilder on(DateTime date) {
        this.event.setStartTime(date);
        return this;
    }

    public EventBuilder til(DateTime date) {
        this.event.setEndTime(date);
        return this;
    }

    public EventBuilder at(Location location) {
        // This will change, don't wanna pass in a location but...
        this.event.setLocation(location);
        return this;
    }

    public PeopleBuilder with() {
        return new PeopleBuilder(this);
    }

    public Event build() {
        return event;
    }
}
