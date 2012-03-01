package com.alexshabanov.rwapp.service.support;

import com.alexshabanov.rwapp.model.user.UserAccount;
import com.alexshabanov.rwapp.service.HelloService;
import com.alexshabanov.rwapp.service.dao.UserAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HelloServiceImpl implements HelloService {
    
    @Autowired
    private UserAccountDao accountDao;

    @Override
    @Transactional(readOnly = true)
    public String getGreeting(String origin) {
        final List<UserAccount> accounts = accountDao.findAll();
        return "Hello, " + origin + " from " + accounts.size() + " account(s)!";
    }
}