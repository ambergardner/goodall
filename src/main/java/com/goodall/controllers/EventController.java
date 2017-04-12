package com.goodall.controllers;

import com.goodall.entities.Event;
import com.goodall.entities.User;
import com.goodall.parsers.RootParser;
import com.goodall.serializers.EventSerializer;
import com.goodall.serializers.RootSerializer;
import com.goodall.serializers.UserSerializer;
import com.goodall.services.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
@CrossOrigin("*")
@RestController
public class EventController {
    @Autowired
    EventRepository events;

    RootSerializer rootSerializer = new RootSerializer();
    EventSerializer eventSerializer = new EventSerializer();

    @RequestMapping(path = "/events", method = RequestMethod.POST)
    public Map<String, Object> createEvent(@RequestBody RootParser<Event> parser, HttpServletResponse response) throws IOException {
        Event inputEvent = parser.getData().getEntity();
//        User user = inputEvent.getUser();
////        inputEvent.setUser(parser.getData().getRelationshipId("user"));
        try {
            events.save(inputEvent);
        } catch (Exception e) {
            response.sendError(400, "Please login.");
        }

        return rootSerializer.serializeOne(
                "/events" + inputEvent.getId(),
                inputEvent,
                eventSerializer
                );
    }
}
