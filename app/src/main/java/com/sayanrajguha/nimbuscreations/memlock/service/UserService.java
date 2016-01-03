package com.sayanrajguha.nimbuscreations.memlock.service;

import com.sayanrajguha.nimbuscreations.memlock.model.User;

import java.util.List;

/**
 * Author # Sayanraj Guha
 * © sayanrajguha@gmail.com
 * ® nimbusCreations
 * Created by SA299562 on 1/3/2016.
 */
public interface UserService {

    boolean addUser(User user);
    boolean updateUser(String username, User updatedUser);
    boolean deleteUser(String username);
    User getUser(String username);
    List<User> getAll();
    boolean authenticate(User user);
}
