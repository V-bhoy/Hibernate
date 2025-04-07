package com.reposirory;

import com.entity.User;

import java.util.List;

public interface UserRepo {
    void insertUser(User user);
    List<User> getUserListByUsingGetForEachInSingleSession(List<Integer> ids);
    List<User> getUserListByFetchingEachInDifferentSession(List<Integer> ids);
    List<User> getUserListByUsingHql(List<Integer> ids);
}
