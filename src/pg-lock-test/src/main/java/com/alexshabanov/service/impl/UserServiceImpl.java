package com.alexshabanov.service.impl;

import com.alexshabanov.service.UserService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Transactional
public final class UserServiceImpl implements UserService {
    @Override
    public int addUserAccount(String name, BigDecimal balance) {
        return 0;
    }
}
