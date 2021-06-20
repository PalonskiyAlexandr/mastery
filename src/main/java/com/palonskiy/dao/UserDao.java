package com.palonskiy.dao;

import com.palonskiy.model.User;

import java.util.Optional;

public interface UserDao{

    Optional<User> findByName(String username);
}
