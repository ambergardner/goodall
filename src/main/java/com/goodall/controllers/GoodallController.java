package com.goodall.controllers;

import com.goodall.entities.Event;
import com.goodall.entities.User;
import com.goodall.entities.ViewUser;
import com.goodall.services.EventRepository;
import com.goodall.services.UserRepository;
import com.goodall.utilities.PasswordStorage;

import jodd.json.JsonParser;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Server;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
public class GoodallController {

    @Autowired
    UserRepository users;

    @Autowired
    EventRepository events;

    @PostConstruct
    public void init() throws SQLException, PasswordStorage.CannotPerformOperationException {
//        dbui = Server.createWebServer().start();
        if (users.count() == 0) {
            User user = new User();
            user.setUsername("Nat");
            user.setPassword("But");
            users.save(user);
        }
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public User login(@RequestBody String body, HttpServletResponse response) throws Exception {
        ViewUser viewUser = null;
        JsonParser p = new JsonParser();
//        Map objectBody = p.parse(body);
//        System.out.println(objectBody);
        try {
            viewUser = p.parse(body, ViewUser.class);
        } catch (Exception e) {
            response.sendError(999, "No user by that name.");
        }

        User dbuser = users.findFirstByUsername(viewUser.getUsername());
        if (dbuser == null) {
            response.sendError(998, "Would you like to create an account?");
        } else if (!dbuser.verifyPassword(viewUser.getPassword())) {
            response.sendError(1234, "Please enter correct password.");
        }
        return dbuser;
    }

//    @RequestMapping(path = "/register", method = RequestMethod.POST)
//    public void registerUser(@RequestBody RootParser<User> user, HttpServletResponse response) throws IOException {
//
//        // Check if already registered
//        try {
////            User user = users.findFirstByUsername(viewUser.getUsername());
//            if (user != null) {
//                response.sendError(978, "Please login or register a new account.");
//            } else {
//                users.save(new User(user.getUsername(), viewUser.getEmail(), viewUser.getPassword()));
//            }
//        } catch (SQLException e) {
//            response.sendError(HttpStatus.FO, "Invalid Login attempt. " + e.getMessage());
//        } catch (Exception e){
//            response.sendError(401, "Invalid: " + e.getMessage());
//        }
//
//    }
//
//    @RequestMapping(path = "/events", method = RequestMethod.POST)
//    public void createEvent(@RequestBody RootParser<Event> event, HttpServletResponse response) throws Exception {
//        try {
//            events.save(event);
//        } catch (Exception e) {
//            response.sendError(777, "Please login.");
//        }
//    }
//
//    @RequestMapping(path = "/delete-event", method = RequestMethod.POST)
//    public void deleteEvent(@RequestBody @Valid Event event, HttpServletResponse response) {
//        events.delete(event);
//    }
//
//    @RequestMapping(path = "/update-event/{id}", method = RequestMethod.POST)
//    public Event updateEvent(@RequestBody @Valid Event event, @PathVariable("id") int id, HttpServletResponse response) throws IOException {
//        try {
//            Event updateEvent = events.findFirstById(id);
//            events.save(updateEvent);
//        } catch (Exception e) {
//            response.sendError(666, "Unable to save update.");
//        }
//        return event;
//    }
}