package com.com.service;

import com.entity.User;

public interface UserService {
    public String insertUser(User user);
    public User getUser(int userId);
    public String updateUser(User user);
    public String deleteUser(int userId);
}
