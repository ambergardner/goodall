package com.goodall.controllers;

import com.goodall.entities.User;
import com.goodall.parsers.RootParser;
import com.goodall.serializers.RootSerializer;
import com.goodall.serializers.UserSerializer;
import com.goodall.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
@CrossOrigin("*")
@RestController
public class UserController {
    @Autowired
    UserRepository users;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    RootSerializer rootSerializer = new RootSerializer();
    UserSerializer userSerializer = new UserSerializer();

    @PostConstruct
    public void init() throws SQLException{
    }

    @RequestMapping(path = "/users", method = RequestMethod.POST)
    public Map<String, Object> registerUser(@RequestBody RootParser<User> parser, HttpServletResponse response) throws IOException{
        User user = parser.getData().getEntity();
        User regUser = new User();
        User dbuser = new User();
        try {
            User checkUsersExists = users.findFirstByUsername(user.getUsername());
            if (checkUsersExists != null || user == null) {
                response.sendError(400, "Please login or register a new account.");
            } else {
                String hashedPassword = bCryptPasswordEncoder.encode(user.getPassword());
                regUser = new User(user.getUsername(), user.getEmail(), hashedPassword);
                dbuser = users.save(regUser);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendError(400, "Invalid Login attempt. ");
        }

        return rootSerializer.serializeOne(
                "/users/" + dbuser.getId(),
                dbuser,
                userSerializer
        );
    }

    @RequestMapping(path = "/users/current", method = RequestMethod.GET)
    public Map<String, Object> currentUser(){
        Authentication u = SecurityContextHolder.getContext().getAuthentication();
        User user = users.findFirstByUsername(u.getName());
        return rootSerializer.serializeOne("/users/" + user.getId(), user, userSerializer);
    }

    @RequestMapping(path = "/users", method = RequestMethod.DELETE)
    public void deleteCurrentUser(){}
}
