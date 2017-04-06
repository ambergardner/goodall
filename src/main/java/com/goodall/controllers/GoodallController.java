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
import java.sql.SQLException;
import java.util.List;

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
        try {
            viewUser = p.parse(body, ViewUser.class);
        }catch(Exception e){
            response.sendError(999, "No user by that name.");
        }

        User dbuser = users.findFirstByUsername(viewUser.getUsername());

        if(dbuser == null){
            response.sendError(401,"Would you like to create an account?");
        }else if(!dbuser.verifyPassword(viewUser.getPassword())){
            response.sendError(401,"Please enter correct password.");
        }
        return dbuser;
    }

    @RequestMapping(path = "/add-user", method = RequestMethod.POST)
    public void
}
