package com.alexshabanov.soaop.service;

import com.alexshabanov.soaop.domain.User;

public interface UserService {
    long registerNewUser();

    User findUser(long id);
}
