package com.goodall.controllers;

import com.goodall.entities.User;
import com.goodall.entities.ViewUser;
import com.goodall.services.EventRepository;
import com.goodall.services.UserRepository;
import com.goodall.utilities.PasswordStorage;

import jodd.json.JsonParser;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Server;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletResponse;
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
        response.sendRedirect("/");
        return dbuser;
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public void addUser(@RequestBody ViewUser viewUser, HttpServletResponse response) throws IOException {
        try {
            User user = users.findFirstByUsername(viewUser.getUsername());
            if(user != null){
                response.sendError(978,"Animals are cool!");
            }else {
                users.save(new User(viewUser.getUsername(), viewUser.getPassword()));
            }
        } catch (Exception e) {
            response.sendError(4321, "This username is not available.");
        }
        response.sendRedirect("/");
    }
}