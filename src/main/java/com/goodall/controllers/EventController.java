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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.View;
import java.io.IOException;
import java.util.ArrayList;
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

    @RequestMapping(path = "/events", method = RequestMethod.GET)//public
    public Map<String, Object> displayEvents() {
        Iterable<Event> showEvents = events.findAll();
        return rootSerializer.serializeMany("/events", showEvents, eventSerializer);
    }

    @RequestMapping(path = "/events/searchzip/{zip}", method = RequestMethod.GET)//public
    public Map<String, Object> filterEventsByZipcode(@PathVariable String zip) {
        ArrayList<Event> filteredEvents = events.findAllByZip(zip);
        return rootSerializer.serializeMany("/events", filteredEvents, eventSerializer);
    }

    @RequestMapping(path = "/events/{id}", method = RequestMethod.GET)//public
    public Map<String, Object> viewEvent(@PathVariable String id, HttpServletResponse response) {
        Event event = events.findFirstById(id);
        return rootSerializer.serializeOne(
                "/events/{id}",
                event,
                eventSerializer);
    }

    @RequestMapping(path = "/events", method = RequestMethod.POST)//private
    public Map<String, Object> createEvent(@RequestBody RootParser<Event> parser, HttpServletResponse response) throws IOException {
        Authentication u = SecurityContextHolder.getContext().getAuthentication();
        Event event = parser.getData().getEntity();
        User user = users.findFirstByUsername(u.getName());
        event.setUser(user);
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

    @RequestMapping(path = "/events/{id}", method = RequestMethod.DELETE)//private
    public void deleteEvent(HttpServletResponse response) throws IOException {
        Authentication u = SecurityContextHolder.getContext().getAuthentication();
        try {
            events.delete(u.getName());
        } catch (Exception e) {
            response.sendError(404, "Event not found");
        }
        response.setStatus(204);
    }

    @RequestMapping(path = "/events/{id}", method = RequestMethod.PATCH)
    public Map<String, Object> updateEvent(@RequestBody RootParser<Event> parser, HttpServletResponse response) throws IOException {
        Authentication u = SecurityContextHolder.getContext().getAuthentication();
        Event event = parser.getData().getEntity();
        User user = users.findFirstByUsername(u.getName());
        event.setUser(user);
        try {
            events.save(event);
        } catch (Exception e) {
            response.sendError(400, "Unable to update event.");
        }
        return rootSerializer.serializeOne(
                "/events",
                event,
                eventSerializer
        );
    }

//    @RequestMapping(path = "/events/location", method = RequestMethod.GET)
//    public Map<String, Object> findEventsByLoc(){
//
//    }
}