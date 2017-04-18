package com.goodall.controllers;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3Client;


import javax.servlet.http.HttpServletResponse;
import javax.swing.text.View;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@CrossOrigin("*")
@RestController
public class EventController {
    @Value("${cloud.aws.s3.bucket}")
    String bucket;

    @Autowired
    AmazonS3Client s3;

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
    public Map<String, Object> createEvent(@RequestParam("file") MultipartFile file,
                                           @RequestParam("address") String address,
                                           @RequestParam ("city")String city,
                                           @RequestParam ("state")String state,
                                           @RequestParam ("zip")String zip,
                                           @RequestParam ("title")String title,
                                           @RequestParam ("artist")String artist,
                                           @RequestParam ("date")String date,
                                           @RequestParam ("description")String description,
                                           @RequestParam ("start-time")String startTime,
                                           @RequestParam ("end-time")String endTime,
                                           HttpServletResponse response) throws IOException {
        Authentication u = SecurityContextHolder.getContext().getAuthentication();
        User user = users.findFirstByUsername(u.getName());

        // create event
        Event event = new Event(address, city, state, zip, title, artist, date,
                description, startTime, endTime, user, "");  // Add all parms, + default image url

        if (file != null) {
            //set the photourl field
            event.setPhotoUrl("https://s3.amazonaws.com/" + bucket + "/" + file.getOriginalFilename());

            // set up s3 request
            PutObjectRequest s3Req = new PutObjectRequest(
                    bucket,
                    file.getOriginalFilename(),
                    file.getInputStream(),
                    new ObjectMetadata());


            // save object to s3
            s3.putObject(s3Req);
        }
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
}