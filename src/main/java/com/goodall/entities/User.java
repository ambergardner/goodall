package com.goodall.entities;

import com.goodall.utilities.PasswordStorage;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User implements HasId {
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    String id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String email;

    @Column
    private String password;



    public User(String username, String email, String password) throws Exception{
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(){}

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String getPasswordHash() {
        return password;
    }

    public String createPasswordHash(String password) throws PasswordStorage.CannotPerformOperationException{
        return PasswordStorage.createHash(password);
    }

    public boolean verifyPassword(String password) throws PasswordStorage.InvalidHashException, PasswordStorage.CannotPerformOperationException {

        return PasswordStorage.verifyPassword(password, getPasswordHash());
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
