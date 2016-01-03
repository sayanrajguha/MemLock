package com.sayanrajguha.nimbuscreations.memlock.service.impl;

import com.sayanrajguha.nimbuscreations.memlock.model.User;
import com.sayanrajguha.nimbuscreations.memlock.service.UserService;

import java.util.List;

/**
 * Author # Sayanraj Guha
 * © sayanrajguha@gmail.com
 * ® nimbusCreations
 * Created by SA299562 on 1/3/2016.
 */
public class UserServiceImpl implements UserService {
    private static final String KEY_LOG = "- User Service IMPL -";

    @Override
    public boolean addUser(User user) {
        return false;
    }

    @Override
    public boolean updateUser(String username, User updatedUser) {
        return false;
    }

    @Override
    public boolean deleteUser(String username) {
        return false;
    }

    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public boolean authenticate(User user) {
        return false;
    }
}
