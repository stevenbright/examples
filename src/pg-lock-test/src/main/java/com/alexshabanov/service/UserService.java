package com.alexshabanov.service;

import java.math.BigDecimal;

public interface UserService {
    int addUserAccount(String name, BigDecimal balance);
}
