package com.goodall.serializers;

import com.goodall.entities.HasId;
import com.goodall.entities.Event;

import java.util.HashMap;
import java.util.Map;

public class EventSerializer extends JsonDataSerializer {

    public String getType() {
        return "events";
    }

    public Map<String, Object> getAttributes(HasId entity) {
        Map<String, Object> result = new HashMap<>();
        Event event = (Event) entity;

        result.put("id", event.getId());
        result.put("title", event.getTitle());
        result.put("address", event.getTitle());
        result.put("city", event.getTitle());
        result.put("state", event.getTitle());
        result.put("zip", event.getZip());
        result.put("description", event.getDescription());
        result.put("start-time", event.getStartTime());
        result.put("end-time", event.getEndTime());
        result.put("artist", event.getArtist());
        result.put("date", event.getDate());
        result.put("user", event.getUser());

        return result;
    }

    public Map<String, String> getRelationshipUrls() {
        return new HashMap<String, String>();
    }
}
