package com.alexshabanov.service.impl;

import com.alexshabanov.service.UserService;
import com.alexshabanov.service.impl.dao.UserAccountDao;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Transactional
public final class UserServiceImpl implements UserService {
    @Resource
    private UserAccountDao userAccountDao;

    @Override
    public int addUserAccount(String name, BigDecimal balance) {
        return userAccountDao.addUserAccount(name, balance);
    }
}
