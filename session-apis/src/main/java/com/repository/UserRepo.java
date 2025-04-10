package com.repository;

import com.entity.User;

import java.util.List;

public interface UserRepo {
    void insertUser(User user);
    User getUser(int userId);
    User loadUser(int userId, boolean needDetails);
}
