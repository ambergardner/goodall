package com.goodall.services;

import com.goodall.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String>{
    User findFirstByUsername(String username);
    User findFirstById(String id);
}
