package com.repository;

import com.entity.User;

public interface UserRepo {
    Boolean insertUser(User user);
    User getUser(int userId);
    Boolean updateUser(User user);
    Boolean deleteUser(int userId);
}
