package com.alexshabanov.gus.service;

import com.alexshabanov.gus.domain.User;

public interface UserService {
    long registerNewUser();

    User findUser(long id);
}
