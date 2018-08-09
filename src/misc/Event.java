package misc;

import types.EventType;

public class Event {
    private EventType eventType;
    private String data;

    public Event(EventType eventType, String data) {
        this.eventType = eventType;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public EventType getEventType() {
        return eventType;
    }
}
