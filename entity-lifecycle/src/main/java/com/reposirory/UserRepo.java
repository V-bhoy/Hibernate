package com.reposirory;

import com.entity.User;

public interface UserRepo {
    void insertUser(User user);
    User getUser(int id);
    void updateUser(User user, int id);
    void updateUserAfterEviction(User user, int id);
    void deleteUser(int id);
}
