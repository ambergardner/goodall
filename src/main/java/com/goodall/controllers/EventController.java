package com.goodall.controllers;

import com.goodall.entities.Event;
import com.goodall.entities.User;
import com.goodall.parsers.RootParser;
import com.goodall.serializers.EventSerializer;
import com.goodall.serializers.RootSerializer;
import com.goodall.services.EventRepository;
import com.goodall.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
        String address = event.getAddress();
        ApiCtl findLoc = new ApiCtl();
        String coordinates = findLoc.makeGeocodeRequest(address);

        try {
            event.setCoordinates(coordinates);
            event.setBgUrl(findLoc.getNasaImageUrl(coordinates));
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
    public Map<String, Object> updateEvent(@PathVariable String id, @RequestBody RootParser<Event> parser, HttpServletResponse response) throws IOException {
        Authentication u = SecurityContextHolder.getContext().getAuthentication();
        User user = users.findFirstByUsername(u.getName());

        Event updatedEvent = parser.getData().getEntity();
        Event dbevent = events.findFirstById(id);

        if (dbevent.getUser().getId() == user.getId()) {
            dbevent.updateTitle(updatedEvent.getTitle());
            dbevent.updateAddress(updatedEvent.getAddress());
            dbevent.updateCity(updatedEvent.getCity());
            dbevent.updateState(updatedEvent.getState());
            dbevent.updateZip(updatedEvent.getZip());
            dbevent.updateArtist(updatedEvent.getArtist());
            dbevent.updateDate(updatedEvent.getDate());
            dbevent.updateDescription(updatedEvent.getDescription());
            dbevent.updateStartTime(updatedEvent.getStartTime());
            dbevent.updateEndTime(updatedEvent.getEndTime());
            dbevent.updateBgUrl(updatedEvent.getBgUrl());
            dbevent.updateCoordinates(updatedEvent.getCoordinates());
        }

        try {
            events.save(dbevent);
        } catch (Exception e) {
            response.sendError(400, "Unable to update event.");
        }
        return rootSerializer.serializeOne(
                "/events/" + dbevent.getId(),
                dbevent,
                eventSerializer
        );
    }

}