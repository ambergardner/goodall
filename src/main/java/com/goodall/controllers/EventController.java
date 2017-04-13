package com.goodall.controllers;

import com.goodall.entities.Event;
import com.goodall.entities.User;
import com.goodall.entities.ViewEvent;
import com.goodall.parsers.RootParser;
import com.goodall.serializers.EventSerializer;
import com.goodall.serializers.RootSerializer;
import com.goodall.serializers.UserSerializer;
import com.goodall.services.EventRepository;
import com.goodall.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.View;
import java.io.IOException;
import java.util.Map;
@CrossOrigin("*")
@RestController
public class EventController {
    @Autowired
    EventRepository events;
    @Autowired
    UserRepository users;

    RootSerializer rootSerializer = new RootSerializer();
    EventSerializer eventSerializer = new EventSerializer();

    @RequestMapping(path = "/events", method = RequestMethod.GET)
    public Map<String, Object> displayEvents(){
        Iterable<Event> showEvents = events.findAll();
        return rootSerializer.serializeMany("/events", showEvents, eventSerializer);
    }

    @RequestMapping(path = "/events", method = RequestMethod.POST)
    public Map<String, Object> createEvent(@RequestBody RootParser<ViewEvent> parser, HttpServletResponse response) throws IOException {
        ViewEvent inputEvent = parser.getData().getEntity();
        User user = users.findFirstById(inputEvent.getUser());
        Event event = new Event(inputEvent.getTitle(), inputEvent.getImgId(), inputEvent.getDescription(), inputEvent.getStartTime(), inputEvent.getEndTime(), inputEvent.getLocation(), inputEvent.getArtist(), inputEvent.getDate(), user);
        try {
            events.save(event);
        } catch (Exception e) {
            response.sendError(400, "Unable to save event.");
        }

        return rootSerializer.serializeOne(
                "/events",
                event,
                eventSerializer
                );
    }

    @RequestMapping(path = "/events/{id}", method = RequestMethod.DELETE)
    public void deleteEvent(@PathVariable String id, HttpServletResponse response) throws IOException {
        try {
            events.delete(id);
        }catch(Exception e){
            response.sendError(404, "Event not found");
        }
        response.setStatus(204);
    }
}