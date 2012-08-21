package com.alexshabanov.soaop.service.support;

import com.alexshabanov.soaop.domain.User;
import com.alexshabanov.soaop.service.UserService;
import org.springframework.stereotype.Service;

@Service
public final class DefaultUserService implements UserService {

    @Override
    public long registerNewUser() {
        // TODO: implement
        return -1;
    }

    @Override
    public User findUser(long id) {
        // TODO: implement
        return null;
    }
}
