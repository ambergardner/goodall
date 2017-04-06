package com.goodall.services;

import com.goodall.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer>{
    User findFirstByUsername(String username);
}
