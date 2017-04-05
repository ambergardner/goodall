package com.goodall.entities;

import javax.persistence.*;

@Entity
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String username;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    String email;
}
