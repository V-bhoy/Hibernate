package com.com.service;

import com.entity.User;
import com.repository.UserRepo;

public class UserServiceImpl implements UserService {
    private UserRepo userRepo;
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public String insertUser(User user) {
        return  userRepo.insertUser(user) ? "success!" : "failed!";
    }

    @Override
    public User getUser(int userId) {
       return userRepo.getUser(userId);
    }

    @Override
    public String updateUser(User user) {
        return userRepo.updateUser(user) ? "success!" : "failed!";
    }

    @Override
    public String deleteUser(int userId) {
        return userRepo.deleteUser(userId) ? "success!" : "failed!";
    }
}
